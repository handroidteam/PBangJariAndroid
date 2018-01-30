package com.handroid.mytownamp.pbangjariandroid;

/**
 * Created by KimJeongMin on 2017-12-17.
 */

public class tmpxml {
String tmp ="타이틀바,앱바, 즐겨찾기를 위한 pc방고유코드(primary key),pc방리스트출력,클릭후 pc방 상세정보,전화예약 또는 좌석보기할건지(경로 축약)";
    String tmp1="pc방 상세 사진정보(스크롤가능한 뷰?,카드뷰?)";
    String tmp2="후기 평점";
    /**
     * package com.example.mytownpcbang.Activity;

     import android.content.SharedPreferences;
     import android.os.AsyncTask;
     import android.os.Bundle;
     import android.support.v7.app.AppCompatActivity;
     import android.util.Log;
     import android.widget.ListView;
     import android.widget.Switch;
     import android.widget.TextView;
     import android.widget.Toast;

     import com.example.mytownpcbang.PcbangArray.PcBang_info;
     import com.example.mytownpcbang.PcbangArray.pcAdapter;
     import com.example.mytownpcbang.R;
     import com.example.mytownpcbang.Server.HttpCallback;
     import com.example.mytownpcbang.Server.Pcbang_uri;

     import org.json.JSONArray;
     import org.json.JSONException;
     import org.json.JSONObject;

     import java.io.BufferedReader;
     import java.io.IOException;
     import java.io.InputStreamReader;
     import java.io.OutputStreamWriter;
     import java.io.PrintWriter;
     import java.net.HttpURLConnection;
     import java.net.URL;
     import java.util.ArrayList;

     public class MainActivity extends AppCompatActivity {


     pcAdapter PcbangAdapter;
     ArrayList<PcBang_info> Pcinfo_arr = new ArrayList<>();
     ListView pcbang_list;
     Switch fav_switch;
     TextView textView;
     SharedPreferences pref;
     SharedPreferences.Editor editor;
     double topLon, bottomLon, leftLat, rightLat;
     JSONObject jsonObject;

     void setLayout() {

     pcbang_list = (ListView) findViewById(R.id.main_fav_listview);
     fav_switch = (Switch) findViewById(R.id.fav_switch);
     textView = (TextView) findViewById(R.id.tmpview);

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
     topLon = 37.588287;
     bottomLon = 37.586287;
     leftLat = 127.009234;
     rightLat = 127.009034;
     try {
     jsonObject = new JSONObject();
     jsonObject.accumulate("topLon", topLon);
     jsonObject.accumulate("bottomLon", bottomLon);
     jsonObject.accumulate("leftLat", leftLat);
     jsonObject.accumulate("rightLat", rightLat);

     } catch (Exception e) {
     e.printStackTrace();
     }

     SetPcBangList();
     }


     public synchronized void SetPcBangList() { //샘플데이터

     HttpRequest httpRequester = new HttpRequest();
     httpRequester.request(Pcbang_uri.pcBang_map_range, httpCallback);

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
     root.getJSONObject(0).getJSONObject("address").getString("postCode"),
     root.getJSONObject(0).getJSONObject("address").getString("roadAddress"),
     root.getJSONObject(0).getString("_id"),
     root.getJSONObject(0).getJSONObject("address").getString("detailAddress"),
     "4",
     root.getJSONObject(0).getJSONObject("location").getString("lat"),
     root.getJSONObject(0).getJSONObject("location").getString("lon")));


     Log.d("fav_data", "호출완료");

     } catch (JSONException d) {

     d.printStackTrace();

     } catch (NullPointerException f) {
     Toast.makeText(MainActivity.this, "데이터 에러", Toast.LENGTH_SHORT).show();
     }
     PcbangAdapter.notifyDataSetChanged();
     }
     };


     public class HttpRequest {

     HttpTask http;

     public void request(String url, HttpCallback callback) {
     http = new HttpRequest.HttpTask(url, callback);
     http.execute();
     }

     public void cancel() {
     if (http != null)
     http.cancel(true);
     }

     private class HttpTask extends AsyncTask<Void, Void, String> {
     String url;
     HttpCallback callback;

     public HttpTask(String url, HttpCallback callback) {
     this.url = url;
     this.callback = callback;
     }

     @Override
     protected String doInBackground(Void... nothing) {
     String response = "";
     String postData = jsonObject.toString();
     PrintWriter pw ;
     BufferedReader in = null;

     //add~~~~~~~~~~~~~~
     try {
     URL text = new URL(url);
     HttpURLConnection http = (HttpURLConnection) text.openConnection();
     http.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
     http.setConnectTimeout(10000);
     http.setReadTimeout(10000);
     http.setRequestMethod("POST");
     http.setDoInput(true);
     http.setDoOutput(true);


     OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
     pw = new PrintWriter(outStream);
     pw.write(postData);
     pw.flush();

     Log.d("fav_data", " " + jsonObject.toString());

     in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
     StringBuffer sb = new StringBuffer();
     String inputLine;
     while ((inputLine = in.readLine()) != null) {
     sb.append(inputLine);
     }
     response = sb.toString();


     } catch (IOException e) {
     e.printStackTrace();
     return null;
     }

     return response;
     }

     @Override
     protected void onPostExecute(String result) {
     //add~~~~~~~~~~~
     this.callback.onResult(result);

     }
     }

     }


     }





     *
     */

}
