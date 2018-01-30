package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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

import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_detail_info;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequester;
import com.handroid.mytownamp.pbangjariandroid.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class Pcbang_detail_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView btn_text_back, text_title, btn_text_fav;
    LinearLayout Layout_pcbang_seat, Layout_pcbang_map, Layout_pcbang_review, Layout_pcbang_event; //좌석/후기/위치
    TextView Pcbang_image, pcbang_info, btn_text_pcspec, btn_text_review_write;
    Button btn_text_event, btn_text_loc, btn_text_review, btn_text_seat;
    ListView review_listview;
    RatingBar pcbang_ratingbar;
    GoogleMap map;
    LinearLayout container;
    View v;
    GridLayout grid;
    //

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;

    LinearLayout pcmap;
    int[] nums = {0, 3, 0, 1, 1, 1, 1, 0, 2, 0, 0, 3, 1, 1, 1, 0, 0, 2, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0};
    String Pcbang_id;
    ArrayList<String> fav_list = new ArrayList<>();
    Pcbang_detail_info pcBang_info;
//

    public void SetLayout() {
        container = (LinearLayout) findViewById(R.id.container);
        btn_text_back = (TextView) findViewById(R.id.btn_text_back);
        text_title = (TextView) findViewById(R.id.text_title);
        btn_text_fav = (TextView) findViewById(R.id.btn_text_fav);
        Layout_pcbang_seat = (LinearLayout) findViewById(R.id.lay_pcbang_Seat);
        Layout_pcbang_map = (LinearLayout) findViewById(R.id.lay_pcbang_map);
        Layout_pcbang_review = (LinearLayout) findViewById(R.id.lay_pcbang_review);
        Layout_pcbang_event = (LinearLayout) findViewById(R.id.lay_pcbang_event);
        review_listview = (ListView) findViewById(R.id.review_listview);
        Pcbang_image = (TextView) findViewById(R.id.Pcbang_image);
        pcbang_info = (TextView) findViewById(R.id.pcbang_info);
        pcbang_ratingbar = (RatingBar) findViewById(R.id.pcbang_rating);
        btn_text_pcspec = (TextView) findViewById(R.id.btn_text_pcspec);
        btn_text_review_write = (TextView) findViewById(R.id.btn_text_review_write);

        btn_text_loc = (Button) findViewById(R.id.btn_text_loc);
        btn_text_review = (Button) findViewById(R.id.btn_text_review);
        btn_text_event = (Button) findViewById(R.id.btn_text_event);
        btn_text_seat = (Button) findViewById(R.id.btn_text_seat);
        btn_text_review_write.setOnClickListener(this);
        btn_text_loc.setOnClickListener(this);
        btn_text_review.setOnClickListener(this);
        btn_text_back.setOnClickListener(this);
        btn_text_fav.setOnClickListener(this);
        btn_text_seat.setOnClickListener(this);
        btn_text_event.setOnClickListener(this);
        mPref = getSharedPreferences("test2", MODE_PRIVATE);
        mEditor = mPref.edit();


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_detail_layout);
        SetLayout();//레이아웃세팅;


//intent 데이터 받기
        Pcbang_id = getIntent().getStringExtra("pcbanginfo");
        SetPcBangList(Pcbang_id);
        //pc방 정보 세팅

        //인플레이트 레이아웃
        v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        pcmap = (LinearLayout) v.findViewById(R.id.Grid);


        container.addView(PCseatView(v, 4, 5, nums));


        String json_fav_list = mPref.getString("fav_list", null); //즐겨찾기 목록 불러오기
        if (json_fav_list != null) {
            try {
                JSONArray fav_array = new JSONArray(json_fav_list);
                for (int i = 0; i < fav_array.length(); i++) {
                    fav_list.add(fav_array.optString(i));

                    if (fav_array.optString(i).equals(Pcbang_id)) { //즐겨찾기 목록에 지금 현 pc방이름과 일치하는 데이터 있는지 확인
                        btn_text_fav.setTag("on"); //태그 on
                        btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_on);
                        Log.d("fav_data", "switch 0n");

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void SetPcBangList(String Pcbang_id) { //샘플데이터

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request(Pcbang_uri.pcBang_search_id + Pcbang_id, httpCallback);

    }

    @Override
    public void onClick(View c) {
        switch (c.getId()) {
            case R.id.btn_text_loc:
                Layout_pcbang_map.setVisibility(View.VISIBLE);
                Layout_pcbang_seat.setVisibility(View.GONE);
                Layout_pcbang_review.setVisibility(View.GONE);
                Layout_pcbang_event.setVisibility(View.GONE);
                break;
            case R.id.btn_text_seat:
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.VISIBLE);
                Layout_pcbang_review.setVisibility(View.GONE);
                Layout_pcbang_event.setVisibility(View.GONE);
                break;
            case R.id.btn_text_review:
                Layout_pcbang_review.setVisibility(View.VISIBLE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.GONE);
                Layout_pcbang_event.setVisibility(View.GONE);

                //review_listview.setAdapter();
                break;
            case R.id.btn_text_review_write:
                Intent reviewIntent = new Intent(Pcbang_detail_Activity.this, Pcbang_review_Activity.class);
                reviewIntent.putExtra("pcbanginfo2", Pcbang_id);
                startActivity(reviewIntent);
                Layout_pcbang_review.setVisibility(View.GONE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_event.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_text_back:
                finish();
                break;
            case R.id.btn_text_fav:
                Shared_Fav();
                break;
            case R.id.btn_text_event:
                Layout_pcbang_event.setVisibility(View.VISIBLE);
                Layout_pcbang_review.setVisibility(View.GONE);
                Layout_pcbang_map.setVisibility(View.GONE);
                Layout_pcbang_seat.setVisibility(View.GONE);
                break;
        }
    }

    public ZoomView PCseatView(View v, int i, int j, int[] num) {
        grid = new GridLayout(this); //새로생성
        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams(container.getLayoutParams()); //view그륩의 layoutparams 가져와서 넣기
        layoutParam.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParam.height = GridLayout.LayoutParams.WRAP_CONTENT;
        grid.setLayoutParams(layoutParam);
        grid.setColumnCount(i); //가로세로 설정
        grid.setRowCount(j);


        int count = 0;
        try {
            for (int c = 0; c < i; c++) {
                for (int d = 0; d < j; d++) {
                    TextView tmps = new TextView(Pcbang_detail_Activity.this);
                    tmps.setHeight(100);
                    tmps.setWidth(100);
                    if (num[count] == 0) {
                        tmps.setBackgroundResource(R.drawable.unusable1);
                    } else if (num[count] == 1) {
                        tmps.setBackgroundResource(R.drawable.usable1);
                    } else if (num[count] == 2) {
                        tmps.setBackgroundResource(R.drawable.checking1);
                    } else {
                        tmps.setText("");
                    }
                    grid.addView(tmps);
                    count++;
                }


            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("asd", "asdasd");
        }
        pcmap.addView(grid);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.zoomTo(2f, 500f, 300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;

    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (map != null) {
            Log.d("위치", "" + pcBang_info.getLatitude() + " " + pcBang_info.getLongitude());
            LatLng latLng = new LatLng(pcBang_info.getLatitude(), (pcBang_info.getLongitude()));
            CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
            map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
            markerOptions.position(latLng);
            markerOptions.title(pcBang_info.getPcBangName());
            markerOptions.snippet("TEL : " + pcBang_info.getpcBangTel());

            map.addMarker(markerOptions);


        }
    }

    public void Shared_Fav() {
        if (btn_text_fav.getTag().toString().equals("on")) {//즐겨찾기 되어잇을시
            final AlertDialog.Builder fav_Alert = new AlertDialog.Builder(Pcbang_detail_Activity.this, MODE_APPEND);
            fav_Alert.setTitle("즐겨찾기");
            fav_Alert.setMessage("즐겨찾기를 해제하시겠습니까?");
            fav_Alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            fav_Alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    for (int c = 0; c < fav_list.size(); c++) {
                        if (fav_list.get(c).equals(Pcbang_id)) {
                            fav_list.remove(c);
                            Log.d("fav_data", "삭제 일치");
                        }
                    }
                    JSONArray tmp = new JSONArray();
                    for (int d = 0; d < fav_list.size(); d++) {
                        tmp.put(fav_list.get(d));
                    }
                    mEditor.putString("fav_list", tmp.toString());
                    mEditor.commit();
                    btn_text_fav.setTag("off");
                    btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_off);
                }
            });
            fav_Alert.show();
        } else {//즐겨찾기 x
            if (fav_list.size() > 3) {
                //크기초과
                Toast.makeText(this, "즐겨찾기는 최대 3개까지 가능합니다.", Toast.LENGTH_SHORT).show();
            } else {
                final String tmpstr = mPref.getString("fav_list", null);

                for (int c = 0; c < fav_list.size(); c++) {
                    if (fav_list.get(c).equals(Pcbang_id)) {
                        fav_list.remove(c);
                    }
                }
                fav_list.add(Pcbang_id);
                JSONArray tmp = new JSONArray();
                for (int d = 0; d < fav_list.size(); d++) {
                    tmp.put(fav_list.get(d));
                }

                mEditor.putString("fav_list", tmp.toString());
                mEditor.commit();
                btn_text_fav.setBackgroundResource(android.R.drawable.btn_star_big_on);
                btn_text_fav.setTag("on");
                Toast.makeText(this, pcBang_info.getPcBangName() + "를 즐겨찾기에 추가하였습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONArray root = new JSONArray(result);
                Log.d("detail_pc", "call" + result);
                pcBang_info = new Pcbang_detail_info(root.getJSONObject(0).getString("pcBangName"),
                        root.getJSONObject(0).getString("tel"),
                        root.getJSONObject(0).getJSONObject("address").getString("postCode"),
                        root.getJSONObject(0).getJSONObject("address").getString("roadAddress"),
                        root.getJSONObject(0).getString("_id"),
                        root.getJSONObject(0).getJSONObject("address").getString("detailAddress"),
                        root.getJSONObject(0).getDouble("ratingScore"),
                        Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lat")),
                        Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lon")),
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("CPU"),
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("RAM"),
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("VGA"));


                Pcbang_image.setText(pcBang_info.getPcBangName());
                pcbang_info.setText(pcBang_info.getdetailAddress() + pcBang_info.getroadAddress());
                btn_text_pcspec.setText("사양 : \n" + "CPU : " + pcBang_info.getCPU() + "\nRAM : " + pcBang_info.getRAM() + "\nVGQ" + pcBang_info.getVGA());
                pcbang_ratingbar.setNumStars(5);
                pcbang_ratingbar.setStepSize(0.5f);
                if (pcBang_info.getratingScore()==null) {
                    pcbang_ratingbar.setRating(0);
                } else {
                    pcbang_ratingbar.setRating(pcBang_info.getratingScore().floatValue());
                }

                ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(Pcbang_detail_Activity.this);
                Log.d("detail_pc", "호출완료");

            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_detail_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }
        }
    };

}