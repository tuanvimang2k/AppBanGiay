package com.example.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.NguoiDungDao;


public class doimatkhau extends AppCompatActivity {
    EditText edtmatkhau, edtmatkhaumoi, edtmatkhaumoi1;
    Button btndangnhap, btndoimatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtmatkhau = findViewById(R.id.edtmatkhau);
        edtmatkhaumoi = findViewById(R.id.edtmatkhaumoi);
        edtmatkhaumoi1 = findViewById(R.id.edtmatkhaumoi1);
        btndangnhap = findViewById(R.id.btndangnhap);
        btndoimatkhau = findViewById(R.id.btndoimatkhau);

        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhau = edtmatkhau.getText().toString();
                String matkhaumoi = edtmatkhaumoi.getText().toString();
                String matkhaumoi1 = edtmatkhaumoi1.getText().toString();
                if (matkhau.equals("") || matkhaumoi.equals("") || matkhaumoi1.equals("")) {
                    Toast.makeText(doimatkhau.this, "Bạn cần nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhaumoi.equals(matkhaumoi1)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String taikhoan = sharedPreferences.getString("taikhoan", "");

                        NguoiDungDao nguoiDungDao = new NguoiDungDao(doimatkhau.this);
                        int check = nguoiDungDao.doimatkhau(taikhoan, matkhau, matkhaumoi);
                        if (check == 1) {
                            Toast.makeText(doimatkhau.this, "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(doimatkhau.this, com.example.myapplication.activity.dangnhap.class);
                          //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (check == 0) {
                            Toast.makeText(doimatkhau.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(doimatkhau.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(doimatkhau.this, "Nhập mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
                //
            }
        });
    }
}