package controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.GioHang;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Giohang;

public class GiohangAdapter extends BaseAdapter {
    ArrayList<Giohang> arrgiohang;
    Context context;

    public GiohangAdapter(ArrayList<Giohang> arrgiohang, Context context) {
        this.arrgiohang = arrgiohang;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrgiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrgiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  class ViewHoder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imgspgiohang;
        public Button btnsl,btntru,btncong;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder=null;
        if(view==null){
            viewHoder =new ViewHoder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_giohang,null);
            viewHoder.txttengiohang=(TextView) view.findViewById(R.id.tvtengiohang);
            viewHoder.txtgiagiohang=(TextView) view.findViewById(R.id.tvgiagiohang);
            viewHoder.imgspgiohang=(ImageView) view.findViewById(R.id.imgspgiohang);
//            viewHoder.btntru=(Button) view.findViewById(R.id.btntru);
//            viewHoder.btncong=(Button) view.findViewById(R.id.btncong);
            viewHoder.btnsl=(Button) view.findViewById(R.id.btnsl);
            view.setTag(viewHoder);
        }
        else {
            viewHoder=(ViewHoder) view.getTag();

        }
        Giohang gh= (Giohang) getItem(i);
        viewHoder.txttengiohang.setText(gh.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###");
        viewHoder.txtgiagiohang.setText(decimalFormat.format(gh.getGiasp())+" Đ");
        Picasso.get().load(gh.getHasp())
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.error)
                .into(viewHoder.imgspgiohang);
        viewHoder.btnsl.setText(String.valueOf(gh.getSoluongsp()));

//        int sl= Integer.parseInt(viewHoder.btnsl.getText().toString());
//        if(sl>=10){
//            viewHoder.btncong.setVisibility(View.INVISIBLE);
//            viewHoder.btntru.setVisibility(View.VISIBLE);
//
//        }else if(sl<=1){
//            viewHoder.btntru.setVisibility(View.INVISIBLE);
//        }
//        else if(sl>1){
//            viewHoder.btncong.setVisibility(View.VISIBLE);
//            viewHoder.btntru.setVisibility(View.VISIBLE);
//        }
        return view;
    }
}