package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.pcAdapter;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-20.
 */

public class Pcbang_Subway_Activity extends Activity {

    TextView btn_close,App_Title,btn_search_sub;

    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_subway_layout);
        mContext=getApplicationContext();
        btn_close= findViewById(R.id.btn_text_back);
        App_Title= findViewById(R.id.text_title);
        btn_search_sub = findViewById(R.id.btn_search_sub);



        pcbang_list= findViewById(R.id.loc_list);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);


        pcbang_list.setAdapter(PcbangAdapter);


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Subway_Activity.this, Pcbang_Detail_Activity.class);
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
                    Pcinfo_arr.add(
                            new PcBang_info(root.getJSONObject(i).getString("pcBangName"),
                                    root.getJSONObject(i).getString("tel"),
                                    root.getJSONObject(i).getJSONObject("address").getString("postCode"),
                                    root.getJSONObject(i).getJSONObject("address").getString("roadAddress"),
                                    root.getJSONObject(i).getString("_id"),
                                    root.getJSONObject(i).getJSONObject("address").getString("detailAddress"),
                                    root.getJSONObject(i).getDouble("ratingScore"),
                                    Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lat")),
                                    Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lon"))));

                }


            } catch (JSONException d) {

                d.printStackTrace();

            }
            catch (NullPointerException f){
                Toast.makeText(Pcbang_Subway_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };



}
