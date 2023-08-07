package com.example.myapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.GiayDAO;
import com.example.myapplication.dao.LoaiGiayDAO;
import com.example.myapplication.model.LoaiGiay;
import com.example.myapplication.model.Product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Giayadapter extends RecyclerView.Adapter<Giayadapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private GiayDAO giayDAO;
    private int maloaigiay;
    int REQUEST_CODE_FOLD = 456;

    public Giayadapter(Context context, ArrayList<Product> list, GiayDAO giayDAO) {
        this.context = context;
        this.list = list;
        this.giayDAO = giayDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_giay_admin, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tengiay.setText(list.get(position).getTengiay());
        holder.gia.setText(String.valueOf(list.get(position).getGia()));
        //        chuyen byte[] thanh bitmap
        byte [] hinh =  list.get(position).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.anh.setImageBitmap(bitmap);

        holder.capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()),holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tengiay,gia;
        ImageView anh;
        Button capNhat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tengiay = itemView.findViewById(R.id.tengiay);
            gia = itemView.findViewById(R.id.gia);
            anh = itemView.findViewById(R.id.anh);
            capNhat = itemView.findViewById(R.id.capNhat);


        }
    }
    public void showDialog(Product product,int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_suagiay,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);
        alertDialog.show();
        ImageView anhGiay = view.findViewById(R.id.anhGiay);
        EditText tengiay = view.findViewById(R.id.tenGiay);
        EditText giaGiay = view.findViewById(R.id.giaGiay);
        EditText mauSac = view.findViewById(R.id.mauSac);
        EditText kichCo = view.findViewById(R.id.kichCo);
        Spinner spnLoaiGiay = view.findViewById(R.id.spnLoaiGiay);
        layDSSpinner(spnLoaiGiay,product.getMaloaigiay());
        maloaigiay = product.getMaloaigiay();
        Button btnHuy = view.findViewById(R.id.btnHuy);
        Button btnSua = view.findViewById(R.id.btnSua);
        anhGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_FOLD);
            }
        });
        spnLoaiGiay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) spnLoaiGiay.getSelectedItem();
                maloaigiay = (int) hashMap.get("maLoaiGiay");
                Toast.makeText(context, ""+maloaigiay, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //        chuyen byte[] thanh bitmap
        byte [] hinh =  list.get(pos).getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        anhGiay.setImageBitmap(bitmap);

        tengiay.setText(product.getTengiay());
        giaGiay.setText(""+product.getGia());
        mauSac.setText(product.getMausac());
        kichCo.setText(""+product.getKichco());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) anhGiay.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,70,byteArrayOutputStream);
                byte [] h_anh = byteArrayOutputStream.toByteArray();
                //                int magiay, String tengiay, int gia, String mausac, int kichco, byte[] anh, int maloaigiay
                String tenGiay_ = tengiay.getText().toString();
                int gia_ = Integer.parseInt(giaGiay.getText().toString());
                String mausac_ = mauSac.getText().toString();
                int kichco_ = Integer.parseInt(kichCo.getText().toString());
                Product product_ = new Product(product.getMagiay(),tenGiay_,gia_,mausac_,kichco_,h_anh,maloaigiay);
                if (giayDAO.suaGiay(product_)){
                    getDS();
                    alertDialog.dismiss();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void layDSSpinner (Spinner spinner, int maLoaiGiay){
        int viTri=-1;
        int position = 0;
        LoaiGiayDAO loaiGiayDAO = new LoaiGiayDAO(context);
        ArrayList<LoaiGiay> list = loaiGiayDAO.getDSLOAI();
        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
        for (LoaiGiay loai : list){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiGiay",loai.getMaloai());
            hashMap.put("tenLoaiGiay",loai.getTenloai());
            listHM.add(hashMap);
            if (loai.getMaloai()==maLoaiGiay){
                viTri = position;
            }
            position++;

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaiGiay"},
                new int[]{android.R.id.text1}
        );
        spinner.setAdapter(simpleAdapter);
        spinner.setSelection(viTri);
    }
    public void getDS(){
        list.clear();
        list = giayDAO.getDSPRO();
        notifyDataSetChanged();
    }

}