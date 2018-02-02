package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.Common.Pcbang_uri;
import com.handroid.mytownamp.pbangjariandroid.R;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpCallback;
import com.handroid.mytownamp.pbangjariandroid.Server.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;


public class Pcbang_Review_Activity extends Activity {

    TextView review_text;
    String Pcbang_id;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbang_review_page);
        review_text = findViewById(R.id.review_text);
        Pcbang_id = getIntent().getStringExtra("pcbanginfo2");
        mContext = getApplicationContext();

        SetPcBangList(Pcbang_id);
    }

    public synchronized void SetPcBangList(String data) { //샘플데이터

        HttpRequest httpRequester = new HttpRequest(httpCallback);
        httpRequester.execute(Pcbang_uri.pcBang_map_info + data);

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
                Toast.makeText(Pcbang_Review_Activity.this, "Server data NULL", Toast.LENGTH_SHORT).show();
            }

        }
    };


}
