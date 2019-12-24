package com.example.dubaothoitiet;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dubaothoitiet.R;
import com.example.dubaothoitiet.Ngay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterNgay extends BaseAdapter {
    Context context;
    ArrayList<Ngay> arrayList;

    public AdapterNgay(Context context, ArrayList<Ngay> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item,null);

        Ngay ngay = arrayList.get(i);

        TextView txtDay = view.findViewById(R.id.txtNgay);
        TextView txtStatus = view.findViewById(R.id.txtTrangThaiNgay);
        TextView txtTempMax = view.findViewById(R.id.txtNhietDoMax);
        TextView txtTempMin = view.findViewById(R.id.txtNhietDoMin);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imgIcon);

        txtDay.setText(ngay.ngay);
        txtStatus.setText(ngay.trangthai);
        txtTempMax.setText(ngay.nhietdoMax+"C");
        txtTempMin.setText(ngay.nhietdoMin+"C");

        Picasso.with(context).load("http://openweathermap.org/img/w/" + ngay.icon + ".png").into(imgStatus);

        return view ;
    }




}
