package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.GiayUserAdapter;
import com.example.myapplication.adapter.Giayadapter;
import com.example.myapplication.adapter.GioHangAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.ItemGioHang;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    RecyclerView recycleviewGioHang;
    GiayDAO dao;
    ArrayList<ItemGioHang> list,listDXN;
    GioHangAdapter adapter;
    TextView title,txtTongTien;
    TextView btnThanhToan;
    int tongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recycleviewGioHang = findViewById(R.id.recycleviewGioHang);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        showList();
       btnThanhToan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               thanhToan();
           }
       });
    }
    private void showList() {
        list = new ArrayList<>();
        dao = new GiayDAO(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan",null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleviewGioHang.setLayoutManager(linearLayoutManager);
        list = dao.layItemGioHang(tk);
        adapter = new GioHangAdapter(this, list, dao,txtTongTien);
        recycleviewGioHang.setAdapter(adapter);
    }
    private void thanhToan(){
        listDXN = new ArrayList<>();
        dao = new GiayDAO(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan",null);
        listDXN = dao.layItemGHDaXacNhan(tk);
        for (ItemGioHang item : listDXN){
            String ten = item.getTen();
            dao.themVaoDH(0,tk,item.getMagiay(),item.getSoLuong());
        }
        dao.xoaItemGHDXN();
        txtTongTien.setText("0 VNĐ");
        showList();

//        
    }
}