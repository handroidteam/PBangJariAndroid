package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;


public class Pcbang_Review_Activity extends Activity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    String Pcbang_Name;
    Context mContext;
    TextView btn_upload, title, btn_text_back;
    EditText edit_username, edit_content, edit_review_title;
    RatingBar ratingBar;
    float ratingnum;
    String review_user, review_content;

    public void settinglayout() {
        Pcbang_Name = getIntent().getStringExtra("pcbangename");
        mContext = getApplicationContext();
        btn_upload = (TextView) findViewById(R.id.btn_upload);
        title = (TextView) findViewById(R.id.text_title);
        btn_text_back = (TextView) findViewById(R.id.btn_text_back);
        btn_text_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_content = (EditText) findViewById(R.id.edit_content);
        edit_review_title = (EditText) findViewById(R.id.edit_review_title);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_review_page);
        settinglayout();
        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);
        // SetPcBangList(Pcbang_Name);
        title.setText(Pcbang_Name);
    }

    public synchronized void SetPcBangList(String data) { //샘플데이터

        //HttpRequest httpRequester = new HttpRequest(Pcbang_Review_Activity.this,"검색중입니다",httpCallback);
        //httpRequester.execute(Pcbang_uri.pcBang_map_info + data);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                Toast.makeText(mContext, "send\n"
                                + "제목 : " + edit_review_title.getText().toString()
                                + "\n이름 : " + edit_username.getText().toString()
                                + "\n내용 : " + edit_content.getText().toString()
                                + "\n별점 : " + ratingBar.getRating()
                        , Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_text_back:
                finish();
                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        ratingnum = v;
        Toast.makeText(mContext, "얼마나 눌렷냐?" + v + "/", Toast.LENGTH_SHORT).show();
    }
}
