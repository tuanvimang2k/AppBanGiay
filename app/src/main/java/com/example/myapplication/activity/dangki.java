package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.NguoiDungDao;


public class dangki extends AppCompatActivity {
    EditText edttaikhoan, edtmatkhau1, edtmatkhau2;
    Button btndangki, btndangnhap,btnShow,btnShowMk2;
    NguoiDungDao nguoiDungDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        btnShow = findViewById(R.id.btnShow);
        btnShowMk2 = findViewById(R.id.btnShowMk2);
        btnShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        edtmatkhau1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        edtmatkhau1.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });

        btnShowMk2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        edtmatkhau2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        edtmatkhau2.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edttaikhoan = findViewById(R.id.edttaikhoan1);
        edtmatkhau1 = findViewById(R.id.edtmatkhau);
        edtmatkhau2 = findViewById(R.id.edtnhaplaimatkhau);
        btndangki = findViewById(R.id.btndangki);
        btndangnhap = findViewById(R.id.btndangnhap);
        nguoiDungDao = new NguoiDungDao(this);
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edttaikhoan.getText().toString();
                String matkhau = edtmatkhau1.getText().toString();
                String pass2 = edtmatkhau2.getText().toString();

                if(matkhau.equals(pass2)){
                    if(nguoiDungDao.them(taikhoan,matkhau,1)){
                        Intent intent = new Intent(dangki.this, com.example.myapplication.activity.dangnhap.class);
                        startActivity(intent);
                        Toast.makeText(dangki.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(dangki.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(dangki.this, "Vui lòng nhập đúng mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
//