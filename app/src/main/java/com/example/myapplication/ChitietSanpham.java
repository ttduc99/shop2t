package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import model.Giohang;
import model.Sanpham;

public class ChitietSanpham extends AppCompatActivity {
Toolbar toolbarctsp;
ImageView imgctsp;
TextView tvten,tvgia,tvmt;
Button btnmua;
Spinner spinsize,spinsl;
    int id=0;
    String ten="";
    int gia=0;
    String ha="",mota="";
    int idsp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        anhxa();
        ActionToolBar();
        Getctsp();
        CatchSpinsl();
        CatchSpinsize();
        EvenBtnmua();
    }

// Them du lieu vao gio hang
    private void EvenBtnmua() {
        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangh.size()>0){
                    int sl=Integer.parseInt(spinsl.getSelectedItem().toString());
                    boolean exist=false;
                    for(int i=0;i<MainActivity.mangh.size();i++){
                        if(MainActivity.mangh.get(i).getIdsp()==id){
                            MainActivity.mangh.get(i).setSoluongsp(MainActivity.mangh.get(i).getSoluongsp()+sl);
                            if(MainActivity.mangh.get(i).getSoluongsp()>=10){
                                MainActivity.mangh.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangh.get(i).setGiasp(gia*MainActivity.mangh.get(i).getSoluongsp());
                             exist=true;
                        }
                    }
                    if(exist==false){
                        int soluong= Integer.parseInt(spinsl.getSelectedItem().toString()) ;
                        long giamoi= soluong*gia;
                        MainActivity.mangh.add(new Giohang(id,ten,giamoi,ha,soluong));
                    }
                }
                else{
                    int soluong= Integer.parseInt(spinsl.getSelectedItem().toString()) ;
                    long giamoi= soluong*gia;
                    MainActivity.mangh.add(new Giohang(id,ten,giamoi,ha,soluong));
                }
                Intent intent= new Intent(getApplicationContext(),com.example.myapplication.GioHang.class);
                startActivity(intent);
            }
        });
    }

    // So luong sp
    private void CatchSpinsl() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinsl.setAdapter(arrayAdapter);
    }
     //size
    private void CatchSpinsize() {
        String[] size= new String[]{"M","L","XL"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,size);
        spinsize.setAdapter(arrayAdapter);
    }

    private void Getctsp() {
        Sanpham sp= (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        id=sp.getID();
        ten=sp.getTensp();
        gia=sp.getGia();
        ha=sp.getHinhanh();
        mota=sp.getMotasp();
        idsp=sp.getIDsp();
        tvten.setText(ten);
        DecimalFormat decimalFormat= new DecimalFormat("###,###");
        tvgia.setText("Giá: "+decimalFormat.format(gia)+" Đ");
        tvmt.setText(mota);
        Picasso.get().load(ha)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.error)
                .into(imgctsp);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarctsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbarctsp=(Toolbar) findViewById(R.id.toolbarctsp);
        imgctsp=(ImageView) findViewById(R.id.imgctsp);
        tvten=(TextView) findViewById(R.id.tenctsp);
        tvgia=(TextView) findViewById(R.id.giactsp);
        tvmt=(TextView) findViewById(R.id.mtctsp);
        btnmua=(Button) findViewById(R.id.btnmua);
        spinsl=(Spinner)findViewById(R.id.spinsl);
        spinsize=(Spinner)findViewById(R.id.spinsize);
    }

}