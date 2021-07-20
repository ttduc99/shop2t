package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controller.CheckConnection;
import controller.Server;

public class ThongtinKH extends AppCompatActivity {
     EditText edtenkh,edsdt,eddiachi;
     Button btnxn,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_k_h);
        anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EvenBtnxn();
        }
        else {
            CheckConnection.ShowTost_Short(getApplicationContext(),"Kiểm tra lại kết nối");
        }
    }

    private void EvenBtnxn() {
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay gia tri edittext bo khoang trang
               final String ten=edtenkh.getText().toString().trim();
                final String sdt=edsdt.getText().toString().trim();
                final  String diachi=eddiachi.getText().toString().trim();
                if(ten.length()>0&&sdt.length()>0&&diachi.length()>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest= new StringRequest(Request.Method.POST, Server.linkdonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                StringRequest request= new StringRequest(Request.Method.POST, Server.linkctdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                                if(response.equals("1")){
                                                    MainActivity.mangh.clear();
                                                    CheckConnection.ShowTost_Short(getApplicationContext(),"Bạn đã mua hàng thành công");
                                                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(intent);
                                                    CheckConnection.ShowTost_Short(getApplicationContext(),"Mời bạn mua tiếp");
                                                }
                                                else {
                                                    CheckConnection.ShowTost_Short(getApplicationContext(),"Lỗi giỏ hàng");

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
                                        JSONArray jsonArray= new JSONArray();
                                        for(int i=0;i<MainActivity.mangh.size();i++){
                                            JSONObject jsonObject= new JSONObject();
                                            try{
                                             jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.mangh.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.mangh.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.mangh.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.mangh.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap= new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                  queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        //thong tin khach hang
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("diachi",diachi);
                            return hashMap;
                        }
                    };
                 requestQueue.add(stringRequest);
                }
                else{
                    CheckConnection.ShowTost_Short(getApplicationContext(),"Kiểm tra lại kết nối");

                }
            }
        });
    }

    private void anhxa() {
        edtenkh=(EditText) findViewById(R.id.edtenkh);
        edsdt=(EditText) findViewById(R.id.edsdtkh);
        eddiachi=(EditText) findViewById(R.id.eddiachi);
        btnxn=(Button) findViewById(R.id.btnxacnhan);
        btntrove=(Button) findViewById(R.id.btntrove);
    }
}