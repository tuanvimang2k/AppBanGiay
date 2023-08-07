package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;
import com.example.myapplication.activity.dangnhap;

public class quangcao extends AppCompatActivity {
    TextView txtbatdau;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quangcao);
        txtbatdau = findViewById(R.id.txtbatdau);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//

        txtbatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(quangcao.this,dangnhap.class));
            }
        });
    }

}