package controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.Loaisp;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListlsp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListlsp, Context context) {
        this.arrayListlsp = arrayListlsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListlsp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListlsp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoder{
   TextView txttenloaisp;
   ImageView imgloaisp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder=null;
        if(view==null){
            viewHoder =new ViewHoder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHoder.txttenloaisp=(TextView) view.findViewById(R.id.tvloaisp);
            viewHoder.imgloaisp=(ImageView) view.findViewById(R.id.imgloaisp);
            view.setTag(viewHoder);
        }
        else {
            viewHoder=(ViewHoder) view.getTag();

        }
        Loaisp loaisp=(Loaisp) getItem(i);
        viewHoder.txttenloaisp.setText((loaisp.getTenloaisp()));
//            viewHoder.imgloaisp.setImageResource(Integer.getInteger(loaisp.getHaloaisp()));
        Picasso.get().load(loaisp.getHaloaisp())
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.error)
                .into(viewHoder.imgloaisp);
        return view;
    }
}
