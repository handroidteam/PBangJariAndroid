package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Subway_Activity extends Activity {

    TextView btn_close, App_Title, btn_search_sub;

    ArrayAdapter PcbangAdapter;
    ArrayList<String> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_subway_layout);
        mContext = getApplicationContext();
        btn_close = findViewById(R.id.btn_text_back);
        App_Title = findViewById(R.id.text_title);
        btn_search_sub = findViewById(R.id.btn_search_sub);

        HttpRequest httpRequest = new HttpRequest(httpCallback);
        httpRequest.execute(Pcbang_uri.pcBang_allceo);

        pcbang_list = findViewById(R.id.loc_list);

        PcbangAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Pcinfo_arr);


        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
              /*  Intent pcbang = new Intent(Pcbang_Subway_Activity.this, Pcbang_Detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position));//pc방 고유 코드
                startActivity(pcbang);*/


                HttpRequest httpRequest = new HttpRequest(httpCallbacks);
                httpRequest.execute(Pcbang_uri.pcBang_map_info + Pcinfo_arr.get(position));

            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_search_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pcbang_Subway_Activity.this, "미구현", Toast.LENGTH_SHORT).show();
            }
        });
    }


    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                Pcinfo_arr.clear(); //서버 데이터 통신
                JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                for (int i = 0; i < root.length(); i++) {
                    Pcinfo_arr.add(root.getJSONObject(i).getString("_id"));
                }


            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Subway_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };

    HttpCallback httpCallbacks = new HttpCallback() {
        @Override
        public void onResult(String result) {

        }
    };


}
