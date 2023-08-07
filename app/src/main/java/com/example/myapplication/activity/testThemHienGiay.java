package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class testThemHienGiay extends AppCompatActivity {
    EditText tengiay, gia, mausac, kichco, maloaigiay;
    ImageView anh;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLD = 456;
    Button chupAnh, layAnh, themGiay;
    GiayDAO giayDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_them_hien_giay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tengiay = findViewById(R.id.tengiay);
        gia = findViewById(R.id.gia);
//        soluong = findViewById(R.id.soluong);
        mausac = findViewById(R.id.mausac);
        kichco = findViewById(R.id.kichco);
        maloaigiay = findViewById(R.id.maloaigiay);
        anh = findViewById(R.id.anh);
        chupAnh = findViewById(R.id.chupAnh);
        layAnh = findViewById(R.id.layAnh);
        themGiay = findViewById(R.id.themAnh);
        giayDAO = new GiayDAO(this);
        chupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);

            }
        });
        layAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLD);
            }
        });
        themGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGiay();
            }
        });
    }
    public void themGiay(){
        themGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tengiay_ = tengiay.getText().toString();
                int gia_ = Integer.parseInt(gia.getText().toString());
//                int soluong_ = Integer.parseInt(soluong.getText().toString());
                String mausac_ = mausac.getText().toString();
                int kichco_ = Integer.parseInt(kichco.getText().toString());
                int maloaigiay_ = Integer.parseInt(maloaigiay.getText().toString());
//              chuyển bitmap thành byte []
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,70,byteArrayOutputStream);
                byte [] h_anh = byteArrayOutputStream.toByteArray();
//                int magiay, String tengiay, int gia, int soluong, String mausac, int kichco, byte[] anh, int maloaigiay
                Product product = new Product(tengiay_,gia_,mausac_,kichco_,h_anh,maloaigiay_);
                if (giayDAO.themGiay(product)){
                    Toast.makeText(testThemHienGiay.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(testThemHienGiay.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            anh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLD && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                anh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}