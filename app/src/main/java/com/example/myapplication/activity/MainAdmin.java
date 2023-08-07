package com.example.myapplication.activity;

import static android.view.Gravity.CENTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.XuLiDonHangActivity;
import com.example.myapplication.adapter.GiayUserAdapter;
import com.example.myapplication.adapter.Giayadapter;
import com.example.myapplication.adapter.LoaiGiayAdapter;
import com.example.myapplication.adapter.LoaiGiayMainAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.dao.NguoiDungDao;
import com.example.myapplication.fragment.fragmentDa;
import com.example.myapplication.fragment.fragmentLeoNui;
import com.example.myapplication.fragment.fragmentTheThao;
import com.example.myapplication.fragment.fragmentThoiTrang;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainAdmin extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recycleview1, recyclerViewLoai;
    private NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    GiayDAO dao;
    Giayadapter giayadapter;
    LoaiGiayDAO loaiGiayDAO;
    LoaiGiayMainAdapter loaiGiayMainAdapter;
    ArrayList<Product> list;
    GiayUserAdapter adapter;
    LinearLayout fragmentchung;
    LoaiGiayAdapter loaiGiayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        TextView all = findViewById(R.id.all);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDS();
            }
        });
//        bottomNavigationView.getMenu().getItem(2).isEnabled(savedInstanceState)=false;

        //navi
        drawerLayout = findViewById(R.id.drawerLayout);


        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        View headerLayout = navigationView.getHeaderView(0);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        getDS();
        showLoaiGiay();

        fragmentchung = findViewById(R.id.fragmentchung);
        fragmentchung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
                int id = item.getItemId();
                switch (id) {
                    case R.id.menuThemGiay:
                        Intent intent = new Intent(MainAdmin.this, TestHienGiay.class);
                        startActivity(intent);
                        break;
                    case R.id.menuThemLoai:
                        Intent intentloaigiay = new Intent(MainAdmin.this, LoaiGiayActivity.class);
                        startActivity(intentloaigiay);
                        break;
                    case R.id.menuXuLiDon:
                        Intent intent1 = new Intent(MainAdmin.this, XuLiDonHangActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menuDoanhThu:

                        Intent intent2 = new Intent(MainAdmin.this, thongkene.class);
                        startActivity(intent2);
                        break;
                    case R.id.menuDangXuat:
                        Intent intent3 = new Intent(MainAdmin.this, dangnhap.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.menuDoiMatKhau:
                        dialogdoimatkhau();
                        break;

                    case R.id.menuThoat:
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startMain);
                        finish();
                        break;

                }
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frameLayout, fragment)
//                            .commit();
//                    toolbar.setTitle(item.getTitle());
//                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialogdoimatkhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainAdmin.this)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);


        EditText edtmatkhau = view.findViewById(R.id.edtmatkhaucu);
        EditText edtmatkhaumoi = view.findViewById(R.id.edtmatkhaumoine);
        EditText edtmatkhaumoi1 = view.findViewById(R.id.edtmatkhaumoine1);
        Button btndoiamtkhau = view.findViewById(R.id.btndoimatkhaune);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        //  alertDialog.setCancelable(false);
        alertDialog.show();
        btndoiamtkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhau = edtmatkhau.getText().toString();
                String matkhaumoi = edtmatkhaumoi.getText().toString();
                String matkhaumoi1 = edtmatkhaumoi1.getText().toString();
                if (matkhau.equals("") || matkhaumoi.equals("") || matkhaumoi1.equals("")) {
                    Toast.makeText(MainAdmin.this, "Bạn cần nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhaumoi.equals(matkhaumoi1)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String taikhoan = sharedPreferences.getString("taikhoan", "");

                        NguoiDungDao nguoiDungDao = new NguoiDungDao(MainAdmin.this);
                        int check = nguoiDungDao.doimatkhau(taikhoan, matkhau, matkhaumoi);
                        if (check == 1) {
                            Toast.makeText(MainAdmin.this, "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            //     Intent intent = new Intent(MainAdmin.this, com.example.myapplication.activity.dangnhap.class);
                            //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //   startActivity(intent);
                        } else if (check == 0) {
                            Toast.makeText(MainAdmin.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainAdmin.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainAdmin.this, "Nhập mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void getDS() {
        recycleview1= findViewById(R.id.recycleview1);
        list = new ArrayList<>();
        dao = new GiayDAO(getApplicationContext());
        GridLayoutManager linearLayoutManager = new GridLayoutManager(MainAdmin.this,2);
        recycleview1.setLayoutManager(linearLayoutManager);
        list = dao.getDSPRO();
        giayadapter = new Giayadapter(this, list, dao);
        recycleview1.setAdapter(giayadapter);

    }

    public void showLoaiGiay() {
        recyclerViewLoai = findViewById(R.id.recyclerViewLoai);
        recycleview1= findViewById(R.id.recycleview1);
        ArrayList<LoaiGiay> listloai;
        loaiGiayDAO = new LoaiGiayDAO(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLoai.setLayoutManager(linearLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        listloai = loaiGiayDAO.getDSLOAI();
        loaiGiayMainAdapter = new LoaiGiayMainAdapter(this, listloai, loaiGiayDAO, recycleview1);
        recyclerViewLoai.setAdapter(loaiGiayMainAdapter);
    }

}