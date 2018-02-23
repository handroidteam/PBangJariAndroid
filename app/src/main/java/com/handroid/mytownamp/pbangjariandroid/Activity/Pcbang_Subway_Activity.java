package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_myloc_adpater;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_myloc_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_subway_adapter;
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

public class Pcbang_Subway_Activity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    TextView btn_close, App_Title, btn_search_sub, btn_search;
    LinearLayout sub_lay1, sub_lay2, sub_lay3;

    Context mContext;

    ListView resultSubway;
    ArrayList<String> resultdata = new ArrayList<>();
    ArrayAdapter adapter2;
    EditText searchEditText;


    ListView Subwaylist;
    Pcbang_subway_adapter adapter;
    ArrayList<String> strings = new ArrayList<>();
    String[] data;


    Double longitude;
    Double latitude;
    Double Lat_range = 0.00438; //약 500m인가
    Double Lon_range = 0.00568;

    JSONObject jsonObject;
    //pc방리스트
    Pcbang_myloc_adpater PcbangAdapter;
    ArrayList<Pcbang_myloc_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;


    private void SettingLayout() {
        //데이터 가져오기
        data = Subway_info.Lines;
        Datainsert(data);


        sub_lay1 = (LinearLayout) findViewById(R.id.sub_lay1);
        sub_lay2 = (LinearLayout) findViewById(R.id.sub_lay2);
        sub_lay3 = (LinearLayout) findViewById(R.id.sub_lay3);
        mContext = getApplicationContext();
        btn_close = findViewById(R.id.btn_text_back);
        App_Title = findViewById(R.id.text_title);
        btn_search_sub = findViewById(R.id.btn_search_sub);
        btn_search = findViewById(R.id.btn_search);
        btn_close.setOnClickListener(this);
        btn_search_sub.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        Subwaylist = (ListView) findViewById(R.id.list);
        adapter = new Pcbang_subway_adapter(mContext, R.layout.pcbang_subway_list_custom, strings);
        Subwaylist.setAdapter(adapter);
        Subwaylist.setOnItemClickListener(this);

        pcbang_list = (ListView) findViewById(R.id.sub_loc_list);
        PcbangAdapter = new Pcbang_myloc_adpater(mContext, R.layout.pcbang_myloc_list_custom, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);
        pcbang_list.setOnItemClickListener(this);

        resultSubway = findViewById(R.id.search_result);
        adapter2 = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, resultdata);
        resultSubway.setAdapter(adapter2);
        resultSubway.setOnItemClickListener(this);

        searchEditText = findViewById(R.id.editText);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_subway_layout);

        SettingLayout();


    }

    @Override
    public void onBackPressed() {
        if (sub_lay2.getVisibility() == View.VISIBLE) {
            sub_lay2.setVisibility(View.GONE);
            sub_lay1.setVisibility(View.VISIBLE);
        } else if (sub_lay3.getVisibility() == View.VISIBLE) {
            sub_lay3.setVisibility(View.GONE);
            sub_lay2.setVisibility(View.GONE);
            sub_lay1.setVisibility(View.VISIBLE);

        } else {
            finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_text_back:
                if (sub_lay2.getVisibility() == View.VISIBLE) {
                    sub_lay2.setVisibility(View.GONE);
                    sub_lay1.setVisibility(View.VISIBLE);
                } else if (sub_lay3.getVisibility() == View.VISIBLE) {
                    sub_lay3.setVisibility(View.GONE);
                    sub_lay2.setVisibility(View.GONE);
                    sub_lay1.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
                break;
            case R.id.btn_search_sub:


                SearchSubway searchSubway = new SearchSubway();
                searchSubway.execute();

                break;
            case R.id.btn_search:
                sub_lay3.setVisibility(View.VISIBLE);
                sub_lay2.setVisibility(View.GONE);
                sub_lay1.setVisibility(View.GONE);
                resultSubway.setVisibility(View.GONE);
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
        Log.d("Sub_data", "" + adapterView.getAdapter().getItem(i));
        final String[] datas = new Subway_info().GetData(data[i]);

        Log.d("list_sub", "리스트item" + adapterView.getAdapter().getItem(i));
        if (data[i].equals(adapterView.getAdapter().getItem(i))) {
            Log.d("list_sub", "1번" + adapterView.getAdapter().getItem(i));
            AlertDialog.Builder a = new AlertDialog.Builder(Pcbang_Subway_Activity.this);
            a.setItems(datas, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    HttpRequests dd = new HttpRequests(search_subwaylocation);
                    dd.execute(Pcbang_uri.pcBang_sub_location + datas[i] + "역");


                }
            });
            a.show();
        } else if (resultdata.size()!=0) {
            if (resultdata.get(i).equals(adapterView.getAdapter().getItem(i))) {
                Log.d("list_sub", "2번" + resultdata.get(i) + "/" + adapterView.getAdapter().getItem(i));
                HttpRequests dd = new HttpRequests(search_subwaylocation);
                dd.execute(Pcbang_uri.pcBang_sub_location + resultdata.get(i) + "역");
            }
        } else {
            Log.d("list_sub", "3번" + "/" + adapterView.getAdapter().getItem(i));
            Intent intent = new Intent(Pcbang_Subway_Activity.this, Pcbang_Detail_Activity.class);
            intent.putExtra("pcbanginfo", Pcinfo_arr.get(i).get_id());
            startActivity(intent);
            finish();
        }


    }

    HttpCallback search_subwaylocation = new HttpCallback() {
        @Override
        public void onResult(String result) {
            sub_lay1.setVisibility(View.GONE);
            sub_lay3.setVisibility(View.GONE);
            sub_lay2.setVisibility(View.VISIBLE);

            longitude = Double.parseDouble(result.split("cacheResponse")[2].split("],")[0].split(",")[1]);
            latitude = Double.parseDouble(result.split("cacheResponse")[2].split("],")[0].split(",")[2]);

            setmyloc(latitude, longitude);

        }
    };

    public void setmyloc(Double latitude, Double longitude) {
        Double topLat = latitude + Lat_range;
        Double bottomLat = latitude - Lat_range;
        Double leftLon = longitude - Lon_range;
        Double rightLon = longitude + Lon_range;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("topLat", topLat);
            jsonObject.put("bottomLat", bottomLat);
            jsonObject.put("leftLon", leftLon);
            jsonObject.put("rightLon", rightLon);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SetPcBangList();
        }
    }

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

    public void SetPcBangList() { //샘플데이터

        HttpRequest httpRequester = new HttpRequest(Pcbang_Subway_Activity.this, "검색중입니다", jsonObject, httpCallback);
        httpRequester.execute(Pcbang_uri.pcBang_map_range);

    }

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            Pcinfo_arr.clear(); //서버 데이터 통신DataNull//
            if (result.equals("DataNull")) {
                Log.d("Sub_data", "No data");
                Toast.makeText(Pcbang_Subway_Activity.this, "500M안에 PC방이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                sub_lay1.setVisibility(View.VISIBLE);
                sub_lay2.setVisibility(View.GONE);
            } else if (result.equals("DataInsertError")) {
                Log.d("Sub_data", "Data Insert Error");
            } else {
                try {
                    JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                    Log.d("Sub_data", "결과" + result);
                    if (root.length() != 0) {
                        for (int i = 0; i < root.length(); i++) {
                            Pcinfo_arr.add(
                                    new Pcbang_myloc_info(root.getJSONObject(i).getString("pcBangName"),
                                            root.getJSONObject(i).getString("tel"),
                                            root.getJSONObject(i).getJSONObject("address").getString("roadAddress"),
                                            root.getJSONObject(i).getString("_id"),
                                            root.getJSONObject(i).getJSONObject("address").getString("detailAddress"),
                                            root.getJSONObject(i).getDouble("ratingScore"),
                                            Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lat")),
                                            Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lon")),
                                            latitude, longitude)
                            );

                        }
                    } else {
                        Log.d("Sub_data", "No data");
                        Toast.makeText(Pcbang_Subway_Activity.this, "500M안에 PC방이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        sub_lay1.setVisibility(View.VISIBLE);
                        sub_lay2.setVisibility(View.GONE);
                    }

                } catch (JSONException d) {
                    d.printStackTrace();
                } catch (NullPointerException f) {
                    Toast.makeText(Pcbang_Subway_Activity.this, "에러", Toast.LENGTH_SHORT).show();
                }
                PcbangAdapter.notifyDataSetChanged();
            }

        }
    };

    public boolean CheckSearchData(String data) {
        for (int i = 0; i < resultdata.size(); i++) {
            if (resultdata.get(i).equals(data)) {
                return true;
            }
        }
        return false;
    }

    public class SearchSubway extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            resultdata.clear();
            adapter2.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {

            String name = searchEditText.getText().toString();
            Log.d("Sub_data", "name = " + name);
            for (int i = 0; i < data.length; i++) {
                String[] datas = new Subway_info().GetData(data[i]);
                for (int j = 0; j < datas.length; j++) {
                    if (datas[j].contains(name) || datas[j].equals(name) || datas[j].matches(".*" + name + ".*")) {
                        if (resultdata.size() != 0) {
                            if (CheckSearchData(datas[j])) {
                                Log.d("Sub_data", "data 중복");
                            } else {
                                resultdata.add(datas[j]);
                            }
                        } else {
                            resultdata.add(datas[j]);
                        }

                    }
                }
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            searchEditText.setText("");
            adapter2.notifyDataSetChanged();
            resultSubway.setVisibility(View.VISIBLE);
        }
    }
}
