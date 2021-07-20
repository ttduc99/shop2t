    package controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class SpNamAdapter extends BaseAdapter {
    ArrayList<Sanpham> arrnam;
    Context context;

    public SpNamAdapter(ArrayList<Sanpham> arrnam, Context context) {
        this.arrnam = arrnam;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrnam.size();
    }

    @Override
    public Object getItem(int i) {
        return arrnam.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoder{
        TextView txttenspnam,txtgiaspnam,txtmtspnam;
        ImageView imgspnam;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder=null;
        if(view==null){
            viewHoder =new ViewHoder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_spnam,null);
            viewHoder.txttenspnam=(TextView) view.findViewById(R.id.tvtenspnam);
            viewHoder.txtgiaspnam=(TextView) view.findViewById(R.id.tvgiaspnam);
            viewHoder.txtmtspnam=(TextView) view.findViewById(R.id.tvmotaspnam);
            viewHoder.imgspnam=(ImageView) view.findViewById(R.id.imgnam);
            view.setTag(viewHoder);
        }
        else {
            viewHoder=(ViewHoder) view.getTag();

        }
        Sanpham spn= (Sanpham) getItem(i);
        viewHoder.txttenspnam.setText(spn.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###");
        viewHoder.txtgiaspnam.setText("Giá: "+decimalFormat.format(spn.getGia())+" Đ");
        viewHoder.txtmtspnam.setMaxLines(2);
        viewHoder.txtmtspnam.setEllipsize(TextUtils.TruncateAt.END);
        viewHoder.txtmtspnam.setText(spn.getMotasp());

//            viewHoder.imgloaisp.setImageResource(Integer.getInteger(loaisp.getHaloaisp()));
        Picasso.get().load(spn.getHinhanh())
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.error)
                .into(viewHoder.imgspnam);
        return view;
    }
}