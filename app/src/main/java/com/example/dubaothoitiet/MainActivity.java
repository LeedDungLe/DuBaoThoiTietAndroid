package com.example.dubaothoitiet;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    TextView txtTenThanhPho,txtSunRise,txtSunSet,txtCoords, txtNhietDo, txtTrangThai, txtDoAm, txtMay, txtGio, txtNgayCapNhat,txtcontent;
    Button btnXacNhanCity, btnNgayKeTiep;
    ImageView imgTrangThai;


    public static AutoCompleteTextView edtSearch;
    String[] dsThanhPho;
    ArrayAdapter<String> adapterThanhPho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }



    private void addEvents() {

        btnXacNhanCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = edtSearch.getText().toString();

                GetWeatherData(city);

            }
        });

        btnNgayKeTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyHienThiCacNgayKeTiep();
            }
        });
    }


    private void xuLyHienThiCacNgayKeTiep() {
        String city = edtSearch.getText().toString();
        Intent intent = new Intent(MainActivity.this, ManHinhCacNgayKeTiepActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }



    public void GetWeatherData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=9956a5636758a4d3c680a758e3d684d8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            String sunrise =jsonObjectSys.getString("sunrise");
                            String sunset =jsonObjectSys.getString("sunset");

                            long l1 = Long.valueOf(sunrise);
                            Date date1 = new Date(l1 * 1000L);
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(" HH:mm");
                            String dayAfterFormat1 = simpleDateFormat1.format(date1);
                            txtSunRise.setText(dayAfterFormat1);

                            long l2 = Long.valueOf(sunset);
                            Date date2 = new Date(l2 * 1000L);
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(" HH:mm");
                            String dayAfterFormat2 = simpleDateFormat2.format(date2);
                            txtSunSet.setText(dayAfterFormat2);
                            //txtTenQuocGia.setText("Tên Quốc Gia: " + country);

                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");

                            txtTenThanhPho.setText( name+" , "+country);
                            txtcontent.setText("Weather in "+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String dayAfterFormat = simpleDateFormat.format(date);
                            txtNgayCapNhat.setText(dayAfterFormat);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgTrangThai);
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            //chuyển nhiệt độ thành 1 số nguyên
                            Double d = Double.valueOf(nhietdo);
                            String nhietDoChuyenSangSoNguyen = String.valueOf(d.intValue());
                            txtNhietDo.setText(nhietDoChuyenSangSoNguyen + "℃");
                            txtDoAm.setText(doam + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String tocDoGio = jsonObjectWind.getString("speed");
                            txtGio.setText(tocDoGio + "m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            txtMay.setText(may+ "%");

                            JSONObject jsonObjectcoords = jsonObject.getJSONObject("coord");
                            String lon=jsonObjectcoords.getString("lon");
                            String lat=jsonObjectcoords.getString("lat");
                            txtCoords.setText("[ "+lon+","+lat+" ]");



                        } catch (JSONException e) {
                            Log.e("LOI", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtSearch = findViewById(R.id.edtSearch);
        txtTenThanhPho = findViewById(R.id.txtTenThanhPho);
        // txtTenQuocGia = findViewById(R.id.txtTenQuocGia);
        txtNhietDo = findViewById(R.id.txtNhietDo);
        txtTrangThai = findViewById(R.id.txtTrangThai);
        txtDoAm = findViewById(R.id.txtDoAm);
        txtMay = findViewById(R.id.txtMay);
        txtGio = findViewById(R.id.txtGio);
        txtNgayCapNhat = findViewById(R.id.txtNgayCapNhat);
        btnXacNhanCity = findViewById(R.id.btnXacNhanCity);
        btnNgayKeTiep = findViewById(R.id.btnNgayKeTiep);
        imgTrangThai = findViewById(R.id.imgTrangThai);
        txtcontent= findViewById(R.id.txtcontent);
        txtSunRise=findViewById(R.id.textviewSunrise);
        txtSunSet=findViewById(R.id.textviewSunset);
        txtCoords=findViewById(R.id.textviewCoord);
        dsThanhPho = getResources().getStringArray(R.array.thanhpho);
        adapterThanhPho = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,dsThanhPho);
        edtSearch.setAdapter(adapterThanhPho);

    }
}

