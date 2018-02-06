package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.Common.Subway_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Subway_Activity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener,OnMapReadyCallback {

    TextView btn_close, App_Title, btn_search_sub;
    Context mContext;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> strings = new ArrayList<>();
    String[] data;
    LinearLayout sub_lay1, sub_lay2;
    GoogleMap map;

    float longitude;
    float latitude;
    String here;


    private void SettingLayout() {
        data = Subway_info.Lines;
        Datainsert(data);

        sub_lay1 = (LinearLayout) findViewById(R.id.sub_lay1);
        sub_lay2 = (LinearLayout) findViewById(R.id.sub_lay2);

        mContext = getApplicationContext();
        btn_close = findViewById(R.id.btn_text_back);
        App_Title = findViewById(R.id.text_title);
        btn_search_sub = findViewById(R.id.btn_search_sub);
        btn_close.setOnClickListener(this);
        btn_search_sub.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, strings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_subway_layout);

        SettingLayout();


    }

    @Override
    public void onBackPressed() {
        if(sub_lay2.getVisibility()==View.VISIBLE){
            sub_lay2.setVisibility(View.GONE);
            sub_lay1.setVisibility(View.VISIBLE);
        }else {
            finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_text_back:
                finish();
                break;
            case R.id.btn_search_sub:
                Toast.makeText(Pcbang_Subway_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void Datainsert(String[] datas) {
        for (int i = 0; i < datas.length; i++) {
            strings.add(datas[i]);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("Sub_data", "click list");
        final String[] datas = new Subway_info().GetData(data[i]);
        AlertDialog.Builder a = new AlertDialog.Builder(Pcbang_Subway_Activity.this);
        a.setItems(datas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Sub_data", "alert click");

                HttpRequests dd = new HttpRequests(zz);
                dd.execute("https://www.google.com/maps/place/" + datas[i] + "역");
                here=datas[i] + "역";

            }
        });
        a.show();

    }

    HttpCallback zz = new HttpCallback() {
        @Override
        public void onResult(String result) {
            sub_lay1.setVisibility(View.GONE);
            sub_lay2.setVisibility(View.VISIBLE);

            longitude = Float.parseFloat(result.split("cacheResponse")[2].split("],")[0].split(",")[1]);
            latitude = Float.parseFloat(result.split("cacheResponse")[2].split("],")[0].split(",")[2]);

            Toast.makeText(Pcbang_Subway_Activity.this, "Latitude" + latitude + "/ Longitude" + longitude, Toast.LENGTH_SHORT).show();
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(Pcbang_Subway_Activity.this);
        }
    };

    public class HttpRequests extends AsyncTask<String, String, String> {

        HttpCallback callback;
        ProgressDialog progressDialog = new ProgressDialog(Pcbang_Subway_Activity.this);

        public HttpRequests(HttpCallback callback) {
            this.callback = callback;

        }



        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Accept", "application/json");
                http.setConnectTimeout(10000);
                http.setReadTimeout(10000);
                http.setRequestMethod("GET");
                http.setDoInput(true);

                Log.d("Sub_data", "http = " + http.getInputStream());
                switch (http.getResponseCode()) {
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        response = "DataNull";
                        break;
                    case HttpURLConnection.HTTP_INTERNAL_ERROR: //데이터 없음
                        response = "DataInsertError";
                        break;
                    case HttpURLConnection.HTTP_OK:
                        response = TransData(http.getInputStream());
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("지하철의 위치를 검색중입니다");
            progressDialog.create();
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            this.callback.onResult(result);
            progressDialog.dismiss();

        }

        public String TransData(InputStream httpStream) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpStream, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                return sb.toString();
            } catch (IOException d) {
                d.printStackTrace();
                return null;
            }

        }

    }
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map != null) {
            Log.d("위치", "" + latitude+ " " + longitude);
            LatLng latLng = new LatLng(latitude, longitude);
            CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(17f).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pcbang_loc_icon));
            markerOptions.position(latLng);
            markerOptions.title(here);

            map.addMarker(markerOptions);


        }
    }

}
