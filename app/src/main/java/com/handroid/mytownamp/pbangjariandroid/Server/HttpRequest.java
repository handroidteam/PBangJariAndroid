package com.handroid.mytownamp.pbangjariandroid.Server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jeongmin on 2018-02-01.
 */


public class HttpRequest extends AsyncTask<String, String, String> {

    HttpCallback callback;
    JSONObject jsonObject;
    String STATE;



    public HttpRequest(HttpCallback callback) {
        this.callback = callback;
        STATE = "GET";
    }

    public HttpRequest(JSONObject jsonObject, HttpCallback callback) {
        this.callback = callback;
        this.jsonObject = jsonObject;
        STATE = "POST";
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setConnectTimeout(10000);
            http.setReadTimeout(10000);

            switch (STATE) {
                case "GET":
                    http.setRequestMethod(STATE);
                    http.setDoInput(true);
                    break;
                case "POST":
                    http.setRequestMethod(STATE);
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    BufferedWriter pw = new BufferedWriter(outStream);
                    pw.write(jsonObject.toString());
                    pw.flush();

                    break;

            }

            switch (http.getResponseCode()) {
                case HttpURLConnection.HTTP_NOT_FOUND:
                    response = "DataNull";
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR: //데이터 없음
                    response = "DataInsertError";
                    break;
                case HttpURLConnection.HTTP_OK:
                    response = TransData(http.getInputStream());
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }




    @Override
    protected void onPostExecute(String result) {
        this.callback.onResult(result);


    }

    public String TransData(InputStream httpStream) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();


        } catch (IOException d) {
            d.printStackTrace();
            return null;
        }

    }

}



