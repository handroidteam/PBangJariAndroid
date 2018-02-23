package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;
import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Favorite_Activity extends Activity implements View.OnClickListener, ListView.OnItemClickListener {

    TextView btn_close, App_Title, btn_search_pc;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    private void setting_layout() {
        btn_close = findViewById(R.id.btn_text_back);
        App_Title = findViewById(R.id.text_title);
        btn_search_pc = findViewById(R.id.btn_search_pc);

        pref = getSharedPreferences("test2", MODE_PRIVATE);
        editor = pref.edit();

        pcbang_list = findViewById(R.id.loc_list);
        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);
        btn_close.setOnClickListener(this);
        pcbang_list.setOnItemClickListener(this);
        mContext=getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_favorite_layout);

        setting_layout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Callfav_list();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text_back:
                finish();
                break;
            case R.id.btn_search_pc:
                Toast.makeText(Pcbang_Favorite_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public synchronized void SetPcBangList(String data) { //샘플데이터
        HttpRequest httpRequester = new HttpRequest(Pcbang_Favorite_Activity.this,"검색중입니다",httpCallback);
        httpRequester.execute(Pcbang_uri.pcBang_search_id + data);
    }

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONArray root = new JSONArray(result);
                Log.d("fav_data", "call" + result);

                Pcinfo_arr.add(
                        new PcBang_info(root.getJSONObject(0).getString("pcBangName"),
                                root.getJSONObject(0).getString("tel"),
                                root.getJSONObject(0).getJSONObject("address").getString("roadAddress"),
                                root.getJSONObject(0).getString("_id"),
                                root.getJSONObject(0).getJSONObject("address").getString("detailAddress"),
                                root.getJSONObject(0).getDouble("ratingScore"),
                                Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lat")),
                                Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lon"))));


                Log.d("fav_data", "호출완료");

            } catch (JSONException d) {
                d.printStackTrace();


            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Favorite_Activity.this, "Server data NULL", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };


    public void Callfav_list() {
        Pcinfo_arr.clear();
        String fav_arr = pref.getString("fav_list", null);
        try {
            JSONArray json_fav_list = new JSONArray(fav_arr);

            if (json_fav_list == null || json_fav_list.length() == 0) {

                Log.d("fav_data", "fav_save_no data");
            } else {
                for (int i = 0; i < json_fav_list.length(); i++) {
                    SetPcBangList(json_fav_list.optString(i));
                }

                Log.d("fav_data", "date ok  : " + fav_arr);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //상세정보 액티비티
        Intent pcbang = new Intent(Pcbang_Favorite_Activity.this, Pcbang_Detail_Activity.class);
        pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(i).get_id());//pc방 고유 코드
        startActivity(pcbang);

    }
}

