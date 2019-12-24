package com.example.dubaothoitiet;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dubaothoitiet.AdapterNgay;
import com.example.dubaothoitiet.Ngay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManHinhCacNgayKeTiepActivity extends AppCompatActivity  {

    ArrayList<Integer> ds=new ArrayList<>();
    TextView txtThanhPho,txtToaDo;
    ImageButton btnBack;

    ListView lvNgay;
    ArrayList<Ngay> dsNgay;
    AdapterNgay adapterNgay;

    String thanhPho = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cac_ngay_ke_tiep);
        addControls();
        addEvents();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        if (city.equals("")) {
            thanhPho = "Hà Nội";
            Get7DaysData(thanhPho);
        }else {
             Get7DaysData(city);
        }

    }


    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }


    private void addControls() {
        txtThanhPho = findViewById(R.id.txtThanhPho);
        btnBack = findViewById(R.id.btnBack);
        lvNgay = findViewById(R.id.lvNgay);
        dsNgay = new ArrayList<Ngay>();
        adapterNgay = new AdapterNgay(ManHinhCacNgayKeTiepActivity.this,dsNgay);
        lvNgay.setAdapter(adapterNgay);
    }


    public void Get7DaysData( String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=9956a5636758a4d3c680a758e3d684d8";
        RequestQueue requestQueue = Volley.newRequestQueue(ManHinhCacNgayKeTiepActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String city = jsonObjectCity.getString("name");
                            txtThanhPho.setText(city);




                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++)
                            {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day = jsonObjectList.getString("dt");

                                long d = Long.valueOf(day);
                                Date date = new Date(d * 1000L);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String dayAfterFormat = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String nhietDoMin = jsonObjectTemp.getString("min");
                                String nhietDoMax = jsonObjectTemp.getString("max");

                                Double min = Double.valueOf(nhietDoMin);
                                Double max = Double.valueOf(nhietDoMax);

                                String nhietDoMinChuyenSangNguyen = String.valueOf(min.intValue());
                                String nhietDoMaxChuyenSangNguyen = String.valueOf(max.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String icon = jsonObjectWeather.getString("icon");
                                String trangthai = jsonObjectWeather.getString("description");
                                int a=Integer.parseInt(nhietDoMaxChuyenSangNguyen);
                                dsNgay.add(new Ngay(dayAfterFormat,trangthai,nhietDoMinChuyenSangNguyen,nhietDoMaxChuyenSangNguyen,icon));
                            }
                            adapterNgay.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
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




}
