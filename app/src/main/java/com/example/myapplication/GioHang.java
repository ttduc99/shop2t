package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

import controller.CheckConnection;
import controller.GiohangAdapter;


public class GioHang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txtthanhtien;
    Button btnthanhtoan,btnmuatiep;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        ActionBar();
        checkData();
        EvenUtil();
        CatchItemlv();
        EvenBtn();
    }

    private void EvenBtn() {
        btnmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangh.size()>0){
                      if(MainActivity.mangh.size()>0){
                         Intent intent= new Intent(getApplicationContext(),ThongtinKH.class);
                         startActivity(intent);
                    }
                  else{
                          CheckConnection.ShowTost_Short(getApplicationContext(),"Bạn chưa mua sản phẩm nào");
                      }
                }
            }
        });
    }

    private void CatchItemlv() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn chắc chắn muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         if(MainActivity.mangh.size()<=0){
                             txtthongbao.setVisibility(View.VISIBLE);
                         }else {
                             MainActivity.mangh.remove(position);
                             giohangAdapter.notifyDataSetChanged();
                             EvenUtil();
                             if(MainActivity.mangh.size()<=0){
                                 txtthongbao.setVisibility(View.VISIBLE);
                             }else{
                                 txtthongbao.setVisibility(View.INVISIBLE);
                             }
                         }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            giohangAdapter.notifyDataSetChanged();
                            EvenUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUtil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.mangh.size();i++){
            tongtien+=MainActivity.mangh.get(i).getGiasp();
        }
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtthanhtien.setText(decimalFormat.format(tongtien)+ " Đ");
    }

    private void checkData() {
        if(MainActivity.mangh.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }
        else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void anhxa() {
        toolbargiohang=(Toolbar) findViewById(R.id.toolbargh);
        lvgiohang=(ListView) findViewById(R.id.lvgiohang);
        txtthongbao=(TextView) findViewById(R.id.tvthongbao);
        txtthanhtien=(TextView) findViewById(R.id.tvtongtien);
        btnthanhtoan=(Button) findViewById(R.id.btnthanhtoan);
        btnmuatiep=(Button)findViewById(R.id.btnmuatiep);
        giohangAdapter=new GiohangAdapter(MainActivity.mangh,GioHang.this);
        lvgiohang.setAdapter(giohangAdapter);
    }
}