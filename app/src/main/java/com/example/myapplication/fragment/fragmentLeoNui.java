package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.ListAllProduct_theoloai;

//
public class fragmentLeoNui extends Fragment {
    TextView xemgiay_theoloai ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_giayleonui, container, false);
        xemgiay_theoloai= view.findViewById(R.id.xemgiay_theoloai);

        xemgiay_theoloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListAllProduct_theoloai.class);
                intent.putExtra("loaigiay",2);
                intent.putExtra("title", "Giày Phượt");
                startActivity(intent);
            }
        });
        return view;
    }
}