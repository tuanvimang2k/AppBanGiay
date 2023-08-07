package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Giayadapter;
import com.example.myapplication.adapter.LoaiGiayAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiGiayActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerViewLoaigiay;
    LoaiGiayDAO dao;
    ArrayList<LoaiGiay> list;
    LayoutInflater inflater;
    View view;
    EditText edttenloaigiay;
    Button btnThem,btnHuy;
    int maloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_giay);
        recyclerViewLoaigiay = findViewById(R.id.recycleviewLoaigiay);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        list = new ArrayList<>();
        dao = new LoaiGiayDAO(LoaiGiayActivity.this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem(list.get(maloai));
            }
        });

        getDS();
    }

    private void showDialogThem(LoaiGiay loaiGiay) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoaiGiayActivity.this);
        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_themloaigiay, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        edttenloaigiay = view.findViewById(R.id.edttenloaigiay);
        btnThem = view.findViewById(R.id.btnThem);
        btnHuy = view.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 maloai = loaiGiay.getMaloai();
                String tenloai = edttenloaigiay.getText().toString();

                LoaiGiay loaiGiay = new LoaiGiay(maloai,tenloai);
                if (dao.themLoaiGiay(loaiGiay)){
                    Toast.makeText(LoaiGiayActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    getDS();
                }else {
                    Toast.makeText(LoaiGiayActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getDS() {

        list = dao.getDSLOAI();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LoaiGiayActivity.this,2);
        recyclerViewLoaigiay.setLayoutManager(gridLayoutManager);
        LoaiGiayAdapter adapter = new LoaiGiayAdapter(LoaiGiayActivity.this, list, dao);
        recyclerViewLoaigiay.setAdapter(adapter);

    }


}