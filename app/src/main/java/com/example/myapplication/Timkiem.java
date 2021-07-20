package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controller.SanphamAdapter;
import controller.SearchAdapter;
import controller.Server;
import model.Sanpham;

public class Timkiem extends AppCompatActivity {
Toolbar toolbarsearch;
ListView lvtimkiem;
RecyclerView recvall;
SearchAdapter searchAdapter;
ArrayList<Sanpham> arrall;
SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);
        anhxa();
        getdataall();
        ActionBar();

    }

    private void ActionBar() {
        setSupportActionBar(toolbarsearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarsearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdataall() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, Server.linkallsp,null, new Response.Listener<JSONArray>() {
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
                            arrall.add(new Sanpham(ID,Tensp,Gia,Motasp,Hinhanh,IDsp));
                            searchAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void anhxa() {
        toolbarsearch=(Toolbar) findViewById(R.id.toolbarsearch);
//        lvtimkiem=(ListView) findViewById(R.id.lvtimkiem);
        recvall= (RecyclerView) findViewById(R.id.recvall);
        arrall= new ArrayList<>();
        searchAdapter=new SearchAdapter(getApplicationContext(),arrall);
        recvall.setHasFixedSize(true);
        recvall.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recvall.setAdapter(searchAdapter);
    }
}