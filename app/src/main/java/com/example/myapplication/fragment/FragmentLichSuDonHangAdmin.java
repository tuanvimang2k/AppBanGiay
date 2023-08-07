package com.example.myapplication.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LichSuAdapter;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.model.ItemDonHang;

import java.util.ArrayList;

public class FragmentLichSuDonHangAdmin extends Fragment {
    GiayDAO giayDAO;
    RecyclerView recycleviewdonhang;
    LichSuAdapter adapter;
    ArrayList<ItemDonHang> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xu_li_don_hang,container,false);
        recycleviewdonhang = view.findViewById(R.id.recycleviewdonhang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycleviewdonhang.setLayoutManager(linearLayoutManager);
        recycleviewdonhang.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();
        giayDAO = new GiayDAO(getContext());
        list = giayDAO.layItemLichSuDH();
        adapter = new LichSuAdapter(getContext(),list,giayDAO);
        recycleviewdonhang.setAdapter(adapter);
        return view;
    }
}
