package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DonHangAdapter;
import com.example.myapplication.adapter.LichSuAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.ItemDonHang;

import java.util.ArrayList;

public class LichSuDonHang extends AppCompatActivity {
    GiayDAO giayDAO;
    RecyclerView recycleviewdonhang;
    LichSuAdapter adapter;
    ArrayList<ItemDonHang> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recycleviewdonhang = findViewById(R.id.recycleviewdonhang);

        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String tk = sharedPreferences.getString("taikhoan", null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleviewdonhang.setLayoutManager(linearLayoutManager);
        recycleviewdonhang.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();
        giayDAO = new GiayDAO(this);
        list = giayDAO.LichSuDonHang(tk);
        adapter = new LichSuAdapter(this,list,giayDAO);

        recycleviewdonhang.setAdapter(adapter);
    }
}