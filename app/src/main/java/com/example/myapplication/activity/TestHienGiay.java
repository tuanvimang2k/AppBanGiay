package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Giayadapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TestHienGiay extends AppCompatActivity {
    RecyclerView recyclerView;
    GiayDAO giayDAO;
    Giayadapter giayadapter;
    ArrayList<Product> list;
    FloatingActionButton floatingActionButton;
    LayoutInflater inflater;
    int maloaigiay_;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLD = 456;
    EditText tengiay, gia, mausac, kichco;
    Spinner maloaigiay;
    ImageView anh;
    Button chupAnh, layAnh, themGiay;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hien_giay);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recycleview);
        list = new ArrayList<>();
        giayDAO = new GiayDAO(this);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

//        inflater =getLayoutInflater();
//        view = inflater.inflate(R.layout.dialog_themgiay_admin,null);

        getDS();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestHienGiay.this);
        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_themgiay_admin, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        EditText tengiay, gia, mausac, kichco;
//        Spinner maloaigiay;
        tengiay = view.findViewById(R.id.tengiay);
        gia = view.findViewById(R.id.gia);
        mausac = view.findViewById(R.id.mausac);
        kichco = view.findViewById(R.id.kichco);
        maloaigiay = view.findViewById(R.id.spnMaloaigiay);
        anh = view.findViewById(R.id.anh);
        layAnh = view.findViewById(R.id.layAnh);
        chupAnh = view.findViewById(R.id.chupAnh);
        themGiay = view.findViewById(R.id.themGiay);
        layAnh();
        chupAnh();
        layDSSpinner(maloaigiay);
        maloaigiay_ = 1;
        maloaigiay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) maloaigiay.getSelectedItem();
                maloaigiay_ = (int) hashMap.get("maLoaiGiay");
                Toast.makeText(TestHienGiay.this, "" + maloaigiay_, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        themGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tengiay_ = tengiay.getText().toString();
                int gia_ = Integer.parseInt(gia.getText().toString());
                String mausac_ = mausac.getText().toString();
                int kichco_ = Integer.parseInt(kichco.getText().toString());


//              chuyển bitmap thành byte []
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
                byte[] h_anh = byteArrayOutputStream.toByteArray();
//                int magiay, String tengiay, int gia, int soluong, String mausac, int kichco, byte[] anh, int maloaigiay
                Product product = new Product(tengiay_, gia_, mausac_, kichco_, h_anh, maloaigiay_);
                if (giayDAO.themGiay(product)) {
                    Toast.makeText(TestHienGiay.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
//                    giayadapter.notifyDataSetChanged();
                    getDS();

                } else {
                    Toast.makeText(TestHienGiay.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chupAnh() {
        chupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);

            }
        });
    }

    private void layAnh() {
        layAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLD);
            }
        });
    }

    private void layDSSpinner(Spinner spinner) {

        LoaiGiayDAO loaiGiayDAO = new LoaiGiayDAO(TestHienGiay.this);
        ArrayList<LoaiGiay> list_ = loaiGiayDAO.getDSLOAI();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiGiay loai_ : list_) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiGiay", loai_.getMaloai());
            hashMap.put("tenLoaiGiay", loai_.getTenloai());
            listHM.add(hashMap);

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                TestHienGiay.this,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaiGiay"},
                new int[]{android.R.id.text1}
        );
        spinner.setAdapter(simpleAdapter);
    }


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


    private void getDS() {
        list = giayDAO.getDSPRO();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TestHienGiay.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        giayadapter = new Giayadapter(this, list, giayDAO);
        recyclerView.setAdapter(giayadapter);

    }

    public void themGiay() {
        themGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tengiay_ = tengiay.getText().toString();
                int gia_ = Integer.parseInt(gia.getText().toString());
                String mausac_ = mausac.getText().toString();
                int kichco_ = Integer.parseInt(kichco.getText().toString());
                maloaigiay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) maloaigiay.getSelectedItem();
                        maloaigiay_ = (int) hashMap.get("maLoaiGiay");
                        Toast.makeText(TestHienGiay.this, "" + maloaigiay, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
//              chuyển bitmap thành byte []
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
                byte[] h_anh = byteArrayOutputStream.toByteArray();
//                int magiay, String tengiay, int gia, int soluong, String mausac, int kichco, byte[] anh, int maloaigiay
                Product product = new Product(tengiay_, gia_, mausac_, kichco_, h_anh, maloaigiay_);
                if (giayDAO.themGiay(product)) {
                    Toast.makeText(TestHienGiay.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TestHienGiay.this, TestHienGiay.class));
                    finish();
                } else {
                    Toast.makeText(TestHienGiay.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}