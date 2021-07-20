package controller;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChitietSanpham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemHolder> implements Filterable {

    Context context;
    ArrayList<Sanpham> arrsp;
    ArrayList<Sanpham> olaarrsp;

    public SearchAdapter( Context context, ArrayList<Sanpham> arrsp) {
        this.context = context;
        this.arrsp = arrsp;
        this.olaarrsp=arrsp;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_allsp,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder( ItemHolder holder, int position) {
        Sanpham sp=arrsp.get(position);
        holder.txttenspall.setText(sp.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiaspal.setText("Giá: "+decimalFormat.format(sp.getGia())+ " Đ");
        Picasso.get().load(sp.getHinhanh())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.error)
                .into(holder.imgspall);
    }

    @Override
    public int getItemCount() {
        return arrsp.size();
    }



    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgspall;
        public TextView txttenspall,txtgiaspal;

        public ItemHolder(View itemView) {
            super(itemView);
            imgspall=(ImageView) itemView.findViewById(R.id.imgall);
            txttenspall=(TextView) itemView.findViewById(R.id.tvtenspall);
            txtgiaspal=(TextView) itemView.findViewById(R.id.tvgiaspall);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChitietSanpham.class);
                    intent.putExtra("thongtinsp",arrsp.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    // cho tim kiem
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch= constraint.toString();
                if(strSearch.isEmpty()){
                    arrsp=olaarrsp;
                }
                else  {
                    ArrayList<Sanpham> list= new ArrayList<>();
                    for(Sanpham sp: olaarrsp){
                        if(sp.getTensp().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(sp);
                        }
                    }
                    arrsp=list;
                }

                FilterResults filterResults= new FilterResults();
                filterResults.values=arrsp;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    arrsp= (ArrayList<Sanpham>) results.values;
                    notifyDataSetChanged();
            }
        };
    }
}