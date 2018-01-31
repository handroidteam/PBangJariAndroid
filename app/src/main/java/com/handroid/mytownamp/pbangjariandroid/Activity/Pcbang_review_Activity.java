package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.PcbangArray.PcBang_info;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequester;
import com.handroid.mytownamp.pbangjariandroid.Server.Pcbang_uri;

import org.json.JSONArray;
import org.json.JSONException;


public class Pcbang_review_Activity extends Activity {

    TextView review_text;
    String Pcbang_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_review_page);
        review_text = (TextView) findViewById(R.id.review_text);
        Pcbang_id = getIntent().getStringExtra("pcbanginfo2");


        SetPcBangList(Pcbang_id);
    }

    public synchronized void SetPcBangList(String data) { //샘플데이터

        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request(Pcbang_uri.pcBang_map_info + data, httpCallback);

    }


    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONArray root = new JSONArray(result);
                Log.d("Review_data", "call" + result);
               /* String tmp = root.getJSONObject(0).getString("pcBangName") + "\n" +
                        root.getJSONObject(0).getString("tel") + "\n" +
                        root.getJSONObject(0).getJSONObject("address").getString("postCode") + "\n" +
                        root.getJSONObject(0).getJSONObject("address").getString("roadAddress") + "\n" +
                        root.getJSONObject(0).getString("_id") + "\n" +
                        root.getJSONObject(0).getJSONObject("address").getString("detailAddress") + "\n" +
                        root.getJSONObject(0).getDouble("ratingScore") + "\n" +
                        Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lat")) + "\n" +
                        Double.parseDouble(root.getJSONObject(0).getJSONObject("location").getString("lon")) + "\n" +
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("CPU") + "\n" +
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("RAM") + "\n" +
                        root.getJSONObject(0).getJSONObject("pcSpec").getString("VGA") + "\n";
*/
                review_text.setText(result);

            } catch (JSONException d) {

                d.printStackTrace();

            } catch (NullPointerException f) {
                Toast.makeText(Pcbang_review_Activity.this, "Server data NULL", Toast.LENGTH_SHORT).show();
            }

        }
    };


}
