package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SerchAdapter;
import com.example.myapplication.dao.SerchDao;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class Serch extends AppCompatActivity {
    private  Context context;
    EditText searchView;
    RecyclerView recyclerView;
      SerchDao serchDao;
      SerchAdapter serchAdapter;
      ArrayList<Product> list;
      ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recycleview1);
        recyclerView.setVisibility(View.GONE);
        list = new ArrayList<>();
        serchDao = new SerchDao(this);
        getDSSerch();
        searchView = findViewById(R.id.Search);
        imgback = findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent(Serch.this,MainKhach.class));
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recyclerView.setVisibility(View.VISIBLE);
                Serch.this.serchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
    private void getDSSerch (){
        list = serchDao.getDS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Serch.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        serchAdapter = new SerchAdapter(this,list,serchDao);
        recyclerView.setAdapter(serchAdapter);

    }




}