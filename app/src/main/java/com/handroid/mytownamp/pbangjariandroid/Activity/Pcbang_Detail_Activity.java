package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.PcbangArray.Pcbang_detail_info;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;
import com.handroid.mytownamp.pbangjariandroid.Common.ViewSeat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-02-02.
 */

public class Pcbang_Detail_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView btn_text_back, text_title, btn_text_fav;
    LinearLayout Layout_pcbang_seat, Layout_pcbang_map, Layout_pcbang_review, Layout_pcbang_event; //좌석/후기/위치
    TextView Pcbang_image, pcbang_info, btn_text_pcspec, btn_text_review_write;
    Button btn_text_event, btn_text_loc, btn_text_review, btn_text_seat;
    ListView review_listview;
    RatingBar pcbang_ratingbar;
    GoogleMap map;
    LinearLayout container;
    View inflateView;
    GridLayout grid;
    //

    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;

    LinearLayout pcmap;
    int[] nums = {0, 3, 0, 1, 1, 1, 1, 0, 2, 0, 0, 3, 1, 1, 1, 0, 0, 2, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0};
    String Pcbang_id;
    ArrayList<String> fav_list = new ArrayList<>();
    Pcbang_detail_info pcBang_info;
    Context mContext;


    public void SetLayout() {
        container = findViewById(R.id.container);
        btn_text_back = findViewById(R.id.btn_text_back);
        text_title = findViewById(R.id.text_title);
        btn_text_fav = findViewById(R.id.btn_text_fav);
        Layout_pcbang_seat = findViewById(R.id.lay_pcbang_Seat);
        Layout_pcbang_map = findViewById(R.id.lay_pcbang_map);
        Layout_pcbang_review = findViewById(R.id.lay_pcbang_review);
        Layout_pcbang_event = findViewById(R.id.lay_pcbang_event);
        review_listview = findViewById(R.id.review_listview);
        Pcbang_image = findViewById(R.id.Pcbang_image);
        pcbang_info = findViewById(R.id.pcbang_info);
        pcbang_ratingbar = findViewById(R.id.pcbang_rating);
        btn_text_pcspec = findViewById(R.id.btn_text_pcspec);
        btn_text_review_write = findViewById(R.id.btn_text_review_write);

        btn_text_loc = findViewById(R.id.btn_text_loc);
        btn_text_review = findViewById(R.id.btn_text_review);
        btn_text_event = findViewById(R.id.btn_text_event);
        btn_text_seat = findViewById(R.id.btn_text_seat);
        btn_text_review_write.setOnClickListener(this);
        btn_text_loc.setOnClickListener(this);
        btn_text_review.setOnClickListener(this);
        btn_text_back.setOnClickListener(this);
        btn_text_fav.setOnClickListener(this);
        btn_text_seat.setOnClickListener(this);
        btn_text_event.setOnClickListener(this);
        mPref = getSharedPreferences("test2", MODE_PRIVATE);
        mEditor = mPref.edit();

        mContext = getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_detail_layout);
        SetLayout();//레이아웃세팅;


//intent 데이터 받기
        Pcbang_id = getIntent().getStringExtra("pcbanginfo");
        SetPcBangList(Pcbang_id);
        //pc방 정보 세팅

        //인플레이트 레이아웃
        inflateView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        pcmap = inflateView.findViewById(R.id.Grid);


        //container.addView(PCseatView(inflateView, 4, 5, nums));
        //container.addView(new ViewSeat().SettingPcMap(this, 4, 5, nums, container, inflateView, pcmap));
        SetFavButtonCheck();




    }
    public void SetFavButtonCheck(){
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
        HttpRequest httpRequester = new HttpRequest(httpCallback);
        httpRequester.execute(Pcbang_uri.pcBang_search_id + Pcbang_id);
    }

    public void SetLoadMap(String Pcbang_id) { //MAP
        HttpRequest httpRequester = new HttpRequest(MapCallback);
        httpRequester.execute(Pcbang_uri.pcBang_map_info + Pcbang_id);

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
                Intent reviewIntent = new Intent(mContext, Pcbang_Review_Activity.class);
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
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pcbang_loc_icon));
            markerOptions.position(latLng);
            markerOptions.title(pcBang_info.getPcBangName());
            markerOptions.snippet("TEL : " + pcBang_info.getpcBangTel());

            map.addMarker(markerOptions);


        }
    }

    public void Shared_Fav() {
        if (btn_text_fav.getTag().toString().equals("on")) {//즐겨찾기 되어잇을시
            final AlertDialog.Builder fav_Alert = new AlertDialog.Builder(Pcbang_Detail_Activity.this, MODE_APPEND);
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
                if (pcBang_info.getratingScore() == null) {
                    pcbang_ratingbar.setRating(0);
                } else {
                    pcbang_ratingbar.setRating(pcBang_info.getratingScore().floatValue());
                }

                ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(Pcbang_Detail_Activity.this);
                Log.d("detail_pc", "호출완료");

            } catch (JSONException d) {
                d.printStackTrace();
                Log.d("detail_pc", "JSON parsing error");

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Detail_Activity.this, "Server data NULL", Toast.LENGTH_SHORT).show();
            }
            finally {
                SetLoadMap("5a7ab5de11daa43e9f4074ec");
            }
        }


    };
    HttpCallback MapCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                int[] d;
                JSONArray root = new JSONArray(result);//즐겨찾기 데이터값
                JSONArray pcinfo = root.getJSONObject(0).getJSONArray("pcInfo");
                Log.d("sub_data", "pcMapTable _id =" + root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("_id"));
                Log.d("sub_data", "pcMapTable sector=" + root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("sector"));
                Log.d("sub_data", "pcMapTable row=" + root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("row"));
                Log.d("sub_data", "pcMapTable col =" + root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("col"));
                int row=Integer.parseInt(root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("row"));
                int col=Integer.parseInt(root.getJSONObject(0).getJSONArray("pcMapTable").getJSONObject(0).getString("col"));
                int size=row*col;
                d=new int[size];
                for (int c = 0; c < pcinfo.length(); c++) {
                   // Log.d("sub_data", "pcInfo _id =" + root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("_id"));
                  //  Log.d("sub_data", "pcInfo sector =" + root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("sector"));
                 //   Log.d("sub_data", "pcInfo pcNumber =" + root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("pcNumber"));
                 //   Log.d("sub_data", "pcInfo pcPlace =" + root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("pcPlace"));
                    int pos=Integer.parseInt(root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("pcPlace"));
                    int value=Integer.parseInt(root.getJSONObject(0).getJSONArray("pcInfo").getJSONObject(c).getString("pcNumber"));
                    d[pos-1]=value;
                }

                Log.d("sub_data", "lastSearchDate  =" + root.getJSONObject(0).getString("lastSearchDate"));
                Log.d("sub_data", "_id =" + root.getJSONObject(0).getString("_id"));
                Log.d("sub_data", "pcBangId =" + root.getJSONObject(0).getString("pcBangId"));
                Log.d("sub_data", "__v =" + root.getJSONObject(0).getString("__v"));

                container.addView(new ViewSeat().SettingPcMap(Pcbang_Detail_Activity.this, row, col, d, container, inflateView, pcmap));

            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_Detail_Activity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
            }



        }
    };
}

