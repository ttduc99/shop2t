package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import controller.Server;

public class Signup extends AppCompatActivity {
    EditText tiphoten, tipemail,tiptk,tipmk;
    Button buttondk;
    TextView tvbackdn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        tiphoten=findViewById(R.id.hoten);
        tipemail= findViewById(R.id.email);
        tiptk= findViewById(R.id.tk);
        tipmk= findViewById(R.id.mk);
        buttondk= findViewById(R.id.btndk);
        tvbackdn=findViewById(R.id.backdn);
        progressBar=findViewById(R.id.progress);

        tvbackdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
            }
        });
        buttondk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten, email, tk, mk;
                hoten = String.valueOf(tiphoten.getText());
                email = String.valueOf(tipemail.getText());
                tk = String.valueOf(tiptk.getText());
                mk = String.valueOf(tipmk.getText());

                if (!hoten.equals("") && !email.equals("") && !tk.equals("") && !mk.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "hoten";
                            field[1] = "email";
                            field[2] = "tk";
                            field[3] = "mk";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = hoten;
                            data[1] = email;
                            data[2] = tk;
                            data[3] = mk;
                            PutData putData = new PutData(Server.linksignup, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                     progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Success")){
                                        Toast.makeText(getApplicationContext(),"đăng ký thành công",Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(getApplicationContext(),LogIn.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                  else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}