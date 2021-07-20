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

public class SpNuAdapter extends BaseAdapter {
    ArrayList<Sanpham> arrnu;
    Context context;

    public SpNuAdapter(ArrayList<Sanpham> arrnu, Context context) {
        this.arrnu = arrnu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrnu.size();
    }

    @Override
    public Object getItem(int i) {
        return arrnu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoder{
        TextView txttenspnu,txtgiaspnu,txtmtspnu;
        ImageView imgspnu;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder=null;
        if(view==null){
            viewHoder =new ViewHoder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_spnu,null);
            viewHoder.txttenspnu=(TextView) view.findViewById(R.id.tvtenspnu);
            viewHoder.txtgiaspnu=(TextView) view.findViewById(R.id.tvgiaspnu);
            viewHoder.txtmtspnu=(TextView) view.findViewById(R.id.tvmotaspnu);
            viewHoder.imgspnu=(ImageView) view.findViewById(R.id.imgnu);
            view.setTag(viewHoder);
        }
        else {
            viewHoder=(ViewHoder) view.getTag();

        }
       Sanpham spn= (Sanpham) getItem(i);
        viewHoder.txttenspnu.setText(spn.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###");
        viewHoder.txtgiaspnu.setText("Giá: "+decimalFormat.format(spn.getGia())+" Đ");
        viewHoder.txtmtspnu.setMaxLines(2);
        viewHoder.txtmtspnu.setEllipsize(TextUtils.TruncateAt.END);
        viewHoder.txtmtspnu.setText(spn.getMotasp());

//            viewHoder.imgloaisp.setImageResource(Integer.getInteger(loaisp.getHaloaisp()));
        Picasso.get().load(spn.getHinhanh())
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.error)
                .into(viewHoder.imgspnu);
        return view;
    }
}