package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.CheckConnection;
import controller.Server;
import controller.SpNuAdapter;
import model.Sanpham;

public class SpNu extends AppCompatActivity {
Toolbar toolbarspnu;
ListView lvspnu;
SpNuAdapter spNuAdapter;
ArrayList<Sanpham> mangspnu;
int idspnu=0;
int page=1;
View footerview;
boolean limitdata=false,isLoading=false;
mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_nu);
        anhxa();
        GetIdLoaisp();
        ActionToolbar();
        getDataspnu(page);
        LoadmoreData();
    }

    private void LoadmoreData() {
        lvspnu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), com.example.myapplication.ChitietSanpham.class);
                intent.putExtra("thongtinsp",mangspnu.get(position));
                startActivity(intent);
            }
        });
        lvspnu.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                   if(firstVisibleItem+visibleItemCount==totalItemCount&& totalItemCount!=0&& isLoading==false&&limitdata==false){
                           isLoading=true;
                           ThreadData threadData=new ThreadData();
                           threadData.start();
                   }
            }
        });
    }

    private void getDataspnu(int page) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url= Server.linksp+ String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String tensp="",mota="";
                int idsp=0,gia=0;
                String ha="";
                if(response!=null&&response.length()!=2){
                    lvspnu.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tensp=jsonObject.getString("tensp");
                            gia=jsonObject.getInt("gia");
                            mota=jsonObject.getString("motasp");
                            ha=jsonObject.getString("hinhanh");
                            idsp=jsonObject.getInt("idsp");
                            mangspnu.add(new Sanpham(id,tensp,gia,mota,ha,idsp));
                            spNuAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitdata=true;
                    lvspnu.removeFooterView(footerview);
                    CheckConnection.ShowTost_Short(getApplicationContext(),"Dữ liệu đã hết");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String>param=new HashMap<String,String>();
                param.put("idsp",String.valueOf(idspnu));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarspnu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarspnu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdLoaisp() {
        idspnu=getIntent().getIntExtra("idloaisp",-1);
        Log.d("giatriloaisp",idspnu+"");
    }

    private void anhxa(){
        toolbarspnu= (Toolbar) findViewById(R.id.toolbarnu);
        lvspnu=(ListView) findViewById(R.id.lvnu);
        mangspnu=new ArrayList<>();
        spNuAdapter=new SpNuAdapter(mangspnu,getApplicationContext());
        lvspnu.setAdapter(spNuAdapter);
        LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar,null);
        mHandler=new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvspnu.addFooterView(footerview);
                    break;
                case 1:
                    getDataspnu(++page);
                    isLoading=false;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shoppingcart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cartgiohang:
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}