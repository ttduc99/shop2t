package controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChitietSanpham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {

    Context context;
    ArrayList<Sanpham> arrsp;

    public SanphamAdapter( Context context, ArrayList<Sanpham> arrsp) {
        this.context = context;
        this.arrsp = arrsp;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spmoi,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder( ItemHolder holder, int position) {
         Sanpham sp=arrsp.get(position);
          holder.txttensp.setText(sp.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+decimalFormat.format(sp.getGia())+ " Đ");
        Picasso.get().load(sp.getHinhanh())
                     .placeholder(R.drawable.ic_launcher_background)
                     .error(R.drawable.error)
                     .into(holder.imgsp);
    }

    @Override
    public int getItemCount() {
        return arrsp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgsp;
        public TextView txttensp,txtgiasp;

        public ItemHolder(View itemView) {
            super(itemView);
            imgsp=(ImageView) itemView.findViewById(R.id.imgsp);
            txttensp=(TextView) itemView.findViewById(R.id.tvtensp);
            txtgiasp=(TextView) itemView.findViewById(R.id.tvgiasp);
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
}
//public class SanphamAdapter extends BaseAdapter {
//    ArrayList<Sanpham> arraySp;
//    Context context;
//
//    public SanphamAdapter(ArrayList<Sanpham> arrayListlsp, Context context) {
//        this.arraySp = arrayListlsp;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return arraySp.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return arraySp.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    public class ViewHoder{
//        TextView txttensp,txtgia;
//        ImageView imgsp;
//    }
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHoder viewHoder=null;
//        if(view==null){
//            viewHoder =new ViewHoder();
//            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view=inflater.inflate(R.layout.dong_spmoi,null);
//            viewHoder.txttensp=(TextView) view.findViewById(R.id.tvtensp);
//            viewHoder.txtgia=(TextView) view.findViewById(R.id.tvgiasp);
//            viewHoder.imgsp=(ImageView) view.findViewById(R.id.imgsp);
//            view.setTag(viewHoder);
//        }
//        else {
//            viewHoder=(ViewHoder) view.getTag();
//            Sanpham sp=(Sanpham) getItem(i);
//            viewHoder.txttensp.setText((sp.getTensp()));
//            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
//       viewHoder.txtgia.setText("Giá: "+decimalFormat.format(sp.getGia())+ " Đ");
////            viewHoder.imgloaisp.setImageResource(Integer.getInteger(loaisp.getHaloaisp()));
//            Picasso.get().load(sp.getHinhanh())
//                    .placeholder((R.drawable.ic_launcher_foreground))
//                    .error(R.drawable.error)
//                    .into(viewHoder.imgsp);
//        }
//        return view;
//    }
//}