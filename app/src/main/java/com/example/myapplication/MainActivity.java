package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.myapplication.adapter.Giayadapter;
import com.example.myapplication.adapter.LoaiGiayAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView,recyclerViewLoai;
     Giayadapter testAdapter;
     LoaiGiayAdapter loaiGiayAdapter;
    ArrayList<Product> list;
     GiayDAO dao;
     LoaiGiayDAO loaiGiayDAO;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recycleview);
        recyclerViewLoai = findViewById(R.id.recycleviewloai);



        showloaigiay();
        showgiay();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = dao.getDSPRO();
//        testAdapter = new testadapter(this,list);
        recyclerView.setAdapter(testAdapter);





    }

    private void showgiay() {
        dao = new GiayDAO(MainActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = dao.getDSPRO();
        testAdapter = new Giayadapter(this,list,dao);
        recyclerView.setAdapter(testAdapter);
    }

    private void showloaigiay() {
        ArrayList<LoaiGiay> listloai;
        loaiGiayDAO = new LoaiGiayDAO(MainActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLoai.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listloai = loaiGiayDAO.getDSLOAI();
        loaiGiayAdapter = new LoaiGiayAdapter(this, listloai,loaiGiayDAO);
        recyclerViewLoai.setAdapter(loaiGiayAdapter);
    }
}