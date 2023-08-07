package com.example.myapplication.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.NguoiDungDao;

public class TrangCaNhanKhach extends AppCompatActivity {

Button btnvetrangchu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan_khach);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      btnvetrangchu = findViewById(R.id.btnvetrangchu);
      TextView  txtdoimatkhau =findViewById(R.id.txtdoimatkhaune);
      TextView txtdonhang = findViewById(R.id.txtdonhang);
      TextView txtchatshop = findViewById(R.id.txtchatshop);
      TextView txtgiohang = findViewById(R.id.txtgiohang);
      TextView txtuser = findViewById(R.id.txtuser);
      TextView txtpagefb = findViewById(R.id.txtfacebook);
      TextView txtsodienthoai = findViewById(R.id.txtsodientoai);
//      txtsodienthoai.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              String sdt = txtsodienthoai.getText().toString();
//            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel"+ sdt));
//            startActivity(intent);
//
//          }
//      });
      txtpagefb.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity( new Intent(TrangCaNhanKhach.this, Webview.class) );
          }
      });
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan", null);
        txtuser.setText(tk);
      txtgiohang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity( new Intent(TrangCaNhanKhach.this, dangnhap.class) );
              finish();
          }
      });
      txtchatshop.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity( new Intent(TrangCaNhanKhach.this, chat_box.class) );
          }
      });
      txtdonhang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity( new Intent(TrangCaNhanKhach.this, LichSuDonHang.class) );
          }
      });
      btnvetrangchu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity( new Intent(TrangCaNhanKhach.this, MainKhach.class) );
          }
      });


        txtdoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogdoimatkhau();
            }
        });

    }
    public  void dialogdoimatkhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TrangCaNhanKhach.this)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);


        EditText edtmatkhau = view.findViewById(R.id.edtmatkhaucu);
        EditText edtmatkhaumoi = view.findViewById(R.id.edtmatkhaumoine);
        EditText edtmatkhaumoi1 = view.findViewById(R.id.edtmatkhaumoine1);
        Button btndoiamtkhau  = view.findViewById(R.id.btndoimatkhaune);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btndoiamtkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhau = edtmatkhau.getText().toString();
                String matkhaumoi = edtmatkhaumoi.getText().toString();
                String matkhaumoi1 = edtmatkhaumoi1.getText().toString();
                if (matkhau.equals("") || matkhaumoi.equals("") || matkhaumoi1.equals("")) {
                    Toast.makeText(TrangCaNhanKhach.this, "Bạn cần nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhaumoi.equals(matkhaumoi1)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String taikhoan = sharedPreferences.getString("taikhoan", "");

                        NguoiDungDao nguoiDungDao = new NguoiDungDao(TrangCaNhanKhach.this);
                        int check = nguoiDungDao.doimatkhau(taikhoan, matkhau, matkhaumoi);
                        if (check == 1) {
                            Toast.makeText(TrangCaNhanKhach.this, "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else if (check == 0) {
                            Toast.makeText(TrangCaNhanKhach.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TrangCaNhanKhach.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TrangCaNhanKhach.this, "Nhập mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}