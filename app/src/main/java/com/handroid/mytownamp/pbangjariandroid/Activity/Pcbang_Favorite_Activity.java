package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
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
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_detail_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequester;
import com.handroid.mytownamp.pbangjariandroid.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Favorite_Activity extends Activity {

    TextView btn_close,App_Title,btn_search_pc;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;
    SharedPreferences pref;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_favorite_layout);

        btn_close=(TextView)findViewById(R.id.btn_text_back);
        App_Title=(TextView)findViewById(R.id.text_title);
        btn_search_pc=(TextView)findViewById(R.id.btn_search_pc);


        pref = getSharedPreferences("test2", MODE_PRIVATE);
        editor = pref.edit();



        pcbang_list=(ListView)findViewById(R.id.loc_list);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);

        Callfav_list();

        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Favorite_Activity.this, Pcbang_detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position).get_id());//pc방 고유 코드
                startActivity(pcbang);


            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_search_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pcbang_Favorite_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public synchronized void SetPcBangList(String data) { //샘플데이터

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request(Pcbang_uri.pcBang_search_id + data, httpCallback);

    }



    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONArray root = new JSONArray(result);
                Log.d("Main_fav_data", "call" + result);

                Pcinfo_arr.add(
                        new PcBang_info(root.getJSONObject(0).getString("pcBangName"),
                                root.getJSONObject(0).getString("tel"),
                                root.getJSONObject(0).getJSONObject("address").getString("postCode"),
                                root.getJSONObject(0).getJSONObject("address").getString("roadAddress"),
                                root.getJSONObject(0).getString("_id"),
                                root.getJSONObject(0).getJSONObject("address").getString("detailAddress"),
                                root.getJSONObject(0).getDouble("ratingScore"),
                                Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lat")),
                                Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lon"))));


                Log.d("Main_fav_data", "호출완료");

            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Favorite_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };


    public void Callfav_list() {
        Pcinfo_arr.clear();
        String fav_arr = pref.getString("fav_list", null);
        if (fav_arr == null) {
            Log.d("Main_fav_data", "fav_save_no data");
        } else {


            Log.d("Main_fav_data", "date ok  : "+fav_arr);
            try {
                JSONArray json_fav_list = new JSONArray(fav_arr);

                for (int i = 0; i < json_fav_list.length(); i++) {
                    SetPcBangList(json_fav_list.optString(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.d("Main_fav_data", "shared  : " + Pcinfo_arr.size());
        }
    }


}

