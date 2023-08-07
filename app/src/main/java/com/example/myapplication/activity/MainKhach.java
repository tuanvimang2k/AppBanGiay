package com.example.myapplication.activity;

import static android.view.Gravity.CENTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SerchAdapter;
import com.example.myapplication.dao.SerchDao;
import com.example.myapplication.fragment.fragmentDa;
import com.example.myapplication.fragment.fragmentLeoNui;
import com.example.myapplication.fragment.fragmentTheThao;
import com.example.myapplication.fragment.fragmentThoiTrang;
import com.example.myapplication.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainKhach extends AppCompatActivity {

    Switch switcher;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LinearLayout fragmentchung;
    private Context context;
    TextView searchView;
   private TextView txtPhut, txtGiay;
   private int timeRemaining = 60;
    RecyclerView recyclerView;
    SerchDao serchDao;
    SerchAdapter serchAdapter;
    ArrayList<Product> list;
// dung da o day roi
    ImageView imageViewHoiDap, imggiohang,chayLSDH;
private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainkhach);
        imageViewHoiDap = findViewById(R.id.imageHoiDap);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtPhut = findViewById(R.id.txtPhutt);
        txtGiay = findViewById(R.id.txtGiayy);
        startTimer();


        //dark mode

        switcher =findViewById(R.id.switchh);
        sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night",false);
        if(nightMODE){
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);

                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        drawerLayout = findViewById(R.id.drawerLayout);
//        test code lsdh
        chayLSDH = findViewById(R.id.chayLSDH);
        chayLSDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainKhach.this,LichSuDonHang.class);
                startActivity(intent);
            }
        });
//        ----------------------------
        chayNavi();
        recyclerView = findViewById(R.id.recycleview1);
        recyclerView.setVisibility(View.GONE);
        list = new ArrayList<>();
        serchDao = new SerchDao(this);
        getDSSerch();
        searchView = findViewById(R.id.edtserch);
        imggiohang = findViewById(R.id.imggiohang);
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainKhach.this, GioHangActivity.class));
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainKhach.this,Serch.class));
            }
        });

        imageViewHoiDap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainKhach.this,chat_box.class);
                startActivity(intent);
            }
        });
//        searchView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                recyclerView.setVisibility(View.VISIBLE);
//                MainKhach.this.serchAdapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });


        LinearLayout lineartheothao = findViewById(R.id.lineartheothao);
        fragmentchung = findViewById(R.id.fragmentchung);
        Fragment fragment = new fragmentTheThao();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentchung, fragment).commit();
        lineartheothao.setBackground(getDrawable(R.drawable.shape4));


        hienfragment();


    }

    private void startTimer() {
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
               txtGiay.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
            if(timeRemaining == 0){
                timeRemaining = 60;
            }
            startTimer();
            timeRemaining = timeRemaining -1;
            txtPhut.setText(""+timeRemaining);

            }
        }.start();
    }

    private void hienfragment() {
        LinearLayout lineartheothao = findViewById(R.id.lineartheothao);
        LinearLayout linearthoitrang = findViewById(R.id.linearthoitrang);
        LinearLayout linearda = findViewById(R.id.linearda);
        LinearLayout linearleonui = findViewById(R.id.linearleonui);
        linearda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentchung = findViewById(R.id.fragmentchung);
                Fragment fragment = new fragmentDa();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentchung, fragment).commit();
                Set();
                SetColer(linearda);

            }


        });
        linearthoitrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentchung = findViewById(R.id.fragmentchung);
                Fragment fragment = new fragmentThoiTrang();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentchung, fragment).commit();
                Set();
                SetColer(linearthoitrang);
            }
        });
        lineartheothao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentchung = findViewById(R.id.fragmentchung);
                Fragment fragment = new fragmentTheThao();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentchung, fragment).commit();
                Set();
                SetColer(lineartheothao);
            }
        });
        linearleonui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentchung = findViewById(R.id.fragmentchung);
                Fragment fragment = new fragmentLeoNui();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentchung, fragment).commit();
                Set();
                SetColer(linearleonui);
            }
        });
    }

    private void SetColer(LinearLayout linearLayout) {
        linearLayout.setBackground(getDrawable(R.drawable.shape4));
        //  linearLayout.setLayoutParams(new LinearLayout.LayoutParams(130, 65));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(CENTER);
        //  linearLayout.setPadding(5, 5, 5, 5);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTextSize(7);

            }

        }
    }

    private void Set() {
        LinearLayout lineartheothao = findViewById(R.id.lineartheothao);
        LinearLayout linearthoitrang = findViewById(R.id.linearthoitrang);
        LinearLayout linearda = findViewById(R.id.linearda);
        LinearLayout linearleonui = findViewById(R.id.linearleonui);

        lineartheothao.setBackground(getDrawable(R.drawable.shapemain3));
        linearthoitrang.setBackground(getDrawable(R.drawable.shapemain3));
        linearda.setBackground(getDrawable(R.drawable.shapemain3));
        linearleonui.setBackground(getDrawable(R.drawable.shapemain3));

        for (int i = 0; i < lineartheothao.getChildCount(); i++) {
            View v = lineartheothao.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTextSize(7);
            }

        }
        for (int i = 0; i < linearthoitrang.getChildCount(); i++) {
            View v = linearthoitrang.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTextSize(7);
            }

        }
        for (int i = 0; i < linearda.getChildCount(); i++) {
            View v = linearda.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTextSize(7);
            }

        }
        for (int i = 0; i < linearleonui.getChildCount(); i++) {
            View v = linearleonui.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTextSize(7);
            }

        }

        lineartheothao.setOrientation(LinearLayout.VERTICAL);
        linearthoitrang.setOrientation(LinearLayout.VERTICAL);
        linearda.setOrientation(LinearLayout.VERTICAL);
        linearleonui.setOrientation(LinearLayout.VERTICAL);

    }
    private void getDSSerch(){
        list = serchDao.getDS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainKhach.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        serchAdapter = new SerchAdapter(this,list,serchDao);
        recyclerView.setAdapter(serchAdapter);

    }
    private void chayNavi(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.miLichSuMuaHang :
                        startActivity(new Intent(MainKhach.this,LichSuDonHang.class));
                        break;
                    case R.id.miTrangCaNhan:
                        startActivity(new Intent(MainKhach.this,TrangCaNhanKhach.class));
                        break;
                }
                return false;
            }
        });

    }

}