package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.NguoiDungDao;

public class dangnhap extends AppCompatActivity {
    //
    Button btndangnhap,btnShow;
    EditText edttaikhoan, edtmatkhau;
    TextView txtdangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        btnShow = findViewById(R.id.btnShow);

        //show pass
        btnShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        edtmatkhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        edtmatkhau.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edttaikhoan = findViewById(R.id.edttaikhoan);
        edtmatkhau = findViewById(R.id.edtmatkhau);
        btndangnhap = findViewById(R.id.btndangnhap);
        txtdangki = findViewById(R.id.txtdangki);
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent(dangnhap.this,dangki.class));
            }
        });


        NguoiDungDao nguoiDungDao = new NguoiDungDao(this);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edttaikhoan.getText().toString();
                String matkhau = edtmatkhau.getText().toString();
                if (nguoiDungDao.kiemtradangnhap(taikhoan,matkhau)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    int a = sharedPreferences.getInt("phanquyen", -1);
                    if(a == 1){
                        startActivity(new Intent(dangnhap.this, MainKhach.class));

                    }else {
                        startActivity(new Intent(dangnhap.this, MainAdmin.class));
                    }
                }else {
                    Toast.makeText(dangnhap.this, "Bạn đã nhập sai Username hoặc PassWord", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}