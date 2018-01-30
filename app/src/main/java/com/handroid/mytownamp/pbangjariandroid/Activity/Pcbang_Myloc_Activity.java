package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.handroid.mytownamp.pbangjariandroid.Gpsinfo;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_detail_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_myloc_adpater;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_myloc_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpPcbangRequest;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequester;
import com.handroid.mytownamp.pbangjariandroid.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Myloc_Activity extends Activity {

    TextView btn_text_back, text_title;

    Pcbang_myloc_adpater PcbangAdapter;
    ArrayList<Pcbang_myloc_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    double topLat, bottomLat, leftLon, rightLon;
    JSONObject jsonObject;
    Gpsinfo gps;
    Double Lat_range = 0.00438;
    Double Lon_range = 0.00568;
    double latitude, longitude;

    public void setting() {
        btn_text_back = (TextView) findViewById(R.id.btn_text_back);
        text_title = (TextView) findViewById(R.id.text_title);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_myloc_layout);
        //레이아웃 세팅
        setting();
        Log.d("data_myloc","oncreate");

        pcbang_list = (ListView) findViewById(R.id.loc_list);
        PcbangAdapter = new Pcbang_myloc_adpater(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Myloc_Activity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position).get_id());//pc방 고유 코드
                startActivity(pcbang);


            }
        });
        btn_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }



    public void onResume(){
        super.onResume();
        Log.d("data_myloc","resume");
        Location_SettingCheck();
    }



    public void Location_SettingCheck(){
        gps = new Gpsinfo(Pcbang_Myloc_Activity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();


            Log.d("myloc_fav_data", "내위치 " + gps.getLatitude() + "/" + gps.getLongitude());

            setmyloc(latitude, longitude);
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }

    }
    public void SetPcBangList() { //샘플데이터

        HttpPcbangRequest httpRequester = new HttpPcbangRequest();
        httpRequester.request(Pcbang_uri.pcBang_map_range, jsonObject, httpCallback);

    }
    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Pcinfo_arr.clear(); //서버 데이터 통신
                JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                Log.d("myloc_fav_data", "결과" + result);
                if(root.length()!=0){
                    for (int i = 0; i < root.length(); i++) {
                        Pcinfo_arr.add(
                                new Pcbang_myloc_info(root.getJSONObject(i).getString("pcBangName"),
                                        root.getJSONObject(i).getString("tel"),
                                        root.getJSONObject(i).getJSONObject("address").getString("postCode"),
                                        root.getJSONObject(i).getJSONObject("address").getString("roadAddress"),
                                        root.getJSONObject(i).getString("_id"),
                                        root.getJSONObject(i).getJSONObject("address").getString("detailAddress"),
                                        root.getJSONObject(i).getDouble("ratingScore"),
                                        Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lat")),
                                        Double.parseDouble(root.getJSONObject(i).getJSONObject("location").getString("lon")),
                                        latitude, longitude)
                        );

                    }
                }else {
                    Toast.makeText(Pcbang_Myloc_Activity.this, "500M안에 PC방이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            } catch (JSONException d) {
                d.printStackTrace();
            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Myloc_Activity.this, "에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };


    public void setmyloc(Double latitude, Double longitude) {
        topLat = latitude + Lat_range;
        bottomLat = latitude - Lat_range;
        leftLon = longitude - Lon_range;
        rightLon = longitude + Lon_range;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("topLat", topLat);
            jsonObject.put("bottomLat", bottomLat);
            jsonObject.put("leftLon", leftLon);
            jsonObject.put("rightLon", rightLon);

            Log.d("myloc_fav_data", "쿼리4요소 : topLat = " + topLat + " bottomLat =  " + bottomLat + " leftLon = " + leftLon + " rightLon= " + rightLon);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SetPcBangList();
        }
    }


}
