package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import controller.Server;

public class LogIn extends AppCompatActivity {
     EditText edtk,edmk;
     Button btndn,btndk;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtk=(EditText) findViewById(R.id.tk);
        edmk=(EditText) findViewById(R.id.mk);
        btndk= (Button)findViewById(R.id.btndk);
        btndn=(Button)findViewById(R.id.btndn);
        progressBar=findViewById(R.id.progress);

        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), com.example.myapplication.Signup.class);
                startActivity(intent2);
                finish();
            }
        });

        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  tk, mk;
                tk = String.valueOf(edtk.getText());
                mk = String.valueOf(edmk.getText());

                if (!tk.equals("") && !mk.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "tk";
                            field[1] = "mk";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = tk;
                            data[1] = mk;
                            PutData putData = new PutData(Server.linklogin, "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Success")){
                                        Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
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