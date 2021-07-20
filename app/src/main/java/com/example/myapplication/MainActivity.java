package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controller.CheckConnection;
import controller.LoaispAdapter;
import controller.SanphamAdapter;
import controller.Server;
import model.Giohang;
import model.Loaisp;
import model.Sanpham;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ArrayList<Sanpham> sanphamArrayList;
    SanphamAdapter sanphamAdapter;

    ImageView imgsearch;
    ListView lvloaisp;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="",haloaisp="";
    public static ArrayList<Giohang> mangh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        actionBar();
        // Chay mang quang cao
        ArrayList<Integer> mangquangcao=new ArrayList<>();
        mangquangcao.add(R.drawable.qc1);
        mangquangcao.add(R.drawable.qc2);
        mangquangcao.add(R.drawable.qc3);
        for(int i=0;i<mangquangcao.size();i++){
            actionFlipper(mangquangcao.get(i),i);
        }


        GetDataSp();
        GetDataLoaisp();
        ChonskLoaisp();

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Timkiem.class);
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_open_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void GetDataLoaisp(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.linkloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try{
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloai");
                            haloaisp=jsonObject.getString("hinhanh");
                            mangloaisp.add(new Loaisp(id,tenloaisp,haloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(new Loaisp(2,"ĐĂNG XUẤT","https://i.ibb.co/8d18bBR/image.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void GetDataSp(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, Server.linkspm,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                if(response!=null){
                    int ID=0;
                    String Tensp="";
                    int Gia=0;
                    String Hinhanh="",Motasp="";
                    int IDsp=0;
                    for(int i=0;i<response.length();i++){
                        try{
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID= jsonObject.getInt("id");
                            Tensp=jsonObject.getString("tensp");
                            Gia=jsonObject.getInt("gia");
                            Motasp=jsonObject.getString("motasp");
                            Hinhanh=jsonObject.getString("hinhanh");
                            IDsp=jsonObject.getInt("idsp");
                            sanphamArrayList.add(new Sanpham(ID,Tensp,Gia,Motasp,Hinhanh,IDsp));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private  void actionFlipper(int imge,int i){
        ImageView imageView=new ImageView(getApplicationContext());
        imageView.setBackgroundResource(imge);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(i==0){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.cleanipedia.com/"));
                    startActivity(intent);
                }
            });
        }
        if(i==1){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://matkinhcan.com/"));
                    startActivity(intent);
                }
            });
        }
        if(i==2){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://mohinhdep.net/"));
                    startActivity(intent);
                }
            });
        }
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }
    private void ChonskLoaisp() {
        lvloaisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,SpNu.class);
                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowTost_Short(MainActivity.this,"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), SpNam.class);
                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowTost_Short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, LogIn.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.ShowTost_Short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
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

    private void anhxa() {
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewflipper);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.recv);
        navigationView=(NavigationView) findViewById(R.id.navig);
        //SP new
        sanphamArrayList=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),sanphamArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);
        //Loai san pham
        mangloaisp=new ArrayList<Loaisp>();
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        lvloaisp =findViewById(R.id.lvloaisp);
        lvloaisp.setAdapter(loaispAdapter);

        if(mangh!=null){
        }
        else{
            mangh=new ArrayList<>();
        }
    imgsearch= findViewById(R.id.imgsearch);
    }

}