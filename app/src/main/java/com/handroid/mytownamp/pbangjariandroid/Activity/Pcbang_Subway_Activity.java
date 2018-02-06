package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.Common.Subway_info;
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

public class Pcbang_Subway_Activity extends Activity implements View.OnClickListener, ListView.OnItemClickListener {

    TextView btn_close, App_Title, btn_search_sub;
    Context mContext;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> strings = new ArrayList<>();
    String[] data;
    LinearLayout sub_lay1, sub_lay2;

    private void SettingLayout() {
        data = Subway_info.Lines;
        Datainsert(data);

        sub_lay1=(LinearLayout)findViewById(R.id.sub_lay1);
        sub_lay2=(LinearLayout)findViewById(R.id.sub_lay2);

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
        Log.d("Sub_data", "oncreate");

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
                Toast.makeText(Pcbang_Subway_Activity.this, datas[i], Toast.LENGTH_SHORT).show();
            }
        });
        a.show();

    }
}
