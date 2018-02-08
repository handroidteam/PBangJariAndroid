package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
 * Created by Jeongmin on 2018-02-02.
 */

public class Pcbang_Main_Activity extends AppCompatActivity {


    pcAdapter PcbangAdapter;
    ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
    ListView pcbang_list;

    TextView textView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;


    void setLayout() {
        mContext = getApplicationContext();

        pcbang_list = findViewById(R.id.main_fav_listview);
        textView = findViewById(R.id.tmpview);

        PcbangAdapter = new pcAdapter(this, R.layout.pcbanglist, Pcinfo_arr);
        pcbang_list.setAdapter(PcbangAdapter);


        pref = getSharedPreferences("test2", MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();

        //Callfav_list();


        pcbang_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //상세정보 액티비티
                Intent pcbang = new Intent(Pcbang_Main_Activity.this, Pcbang_Detail_Activity.class);
                pcbang.putExtra("pcbanginfo", Pcinfo_arr.get(position).get_id());//pc방 고유 코드
                startActivity(pcbang);

            }
        });


    }


    //pc방리스트 보여주는 액티비티
    public void MainButton(View v) {
        switch (v.getId()) {
            case R.id.btn_myloc://내위치
                Intent intent1 = new Intent(Pcbang_Main_Activity.this, Pcbang_Myloc_Activity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.activityup, R.anim.activitydown);

                break;
            case R.id.btn_sub://지하철역
                Intent intent2 = new Intent(Pcbang_Main_Activity.this, Pcbang_Subway_Activity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.activityleft, R.anim.activityright);
                break;
            case R.id.btn_event://이벤트
                Intent intent3 = new Intent(Pcbang_Main_Activity.this, Pcbang_Event_Activity.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.activityleft, R.anim.activityright);
                break;
            case R.id.btn_fav://즐겨찾기
                Intent intent4 = new Intent(Pcbang_Main_Activity.this, Pcbang_Favorite_Activity.class);
                startActivity(intent4);
                overridePendingTransition(R.anim.activityup, R.anim.activitydown);
                break;
        }

    }


    public synchronized void SetPcBangList(String data) { //샘플데이터

        HttpRequest httpRequester = new HttpRequest(Pcbang_Main_Activity.this,"검색중입니다",httpCallback);
        httpRequester.execute(Pcbang_uri.pcBang_search_id + data);

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
                Toast.makeText(Pcbang_Main_Activity.this, "Server data NULL", Toast.LENGTH_SHORT).show();
            }
            PcbangAdapter.notifyDataSetChanged();
        }
    };

    public void onResume() {
        super.onResume();
        Callfav_list();
    }

    public void Callfav_list() {
        Pcinfo_arr.clear();
        String fav_arr = pref.getString("fav_list", null);
        if (fav_arr != null) {
            try {
                JSONArray json_fav_list = new JSONArray(fav_arr);

                if (json_fav_list.length() == 0) {

                    textView.setVisibility(View.VISIBLE);
                    pcbang_list.setVisibility(View.GONE);
                    Log.d("Main_fav_data", "fav_save_no data");
                } else {

                    pcbang_list.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);

                    for (int i = 0; i < json_fav_list.length(); i++) {
                        SetPcBangList(json_fav_list.optString(i));
                    }

                    Log.d("Main_fav_data", "date ok  : " + fav_arr);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException d) {
                d.printStackTrace();
            }

        }
        else {
            textView.setVisibility(View.VISIBLE);
            pcbang_list.setVisibility(View.GONE);
            Log.d("Main_fav_data", "fav_save_no data");
        }
    }


}
