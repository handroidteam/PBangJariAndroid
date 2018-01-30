package com.handroid.mytownamp.pbangjariandroid.Server;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPcbangRequest {

    HttpTask http;
    JSONObject jsonObject;

    public void request(String url,JSONObject jsonObject, HttpCallback callback) {
        this.jsonObject=jsonObject;
        http = new HttpTask(url, callback);
        http.execute();
    }

    public void cancel() {
        if(http != null)
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

            BufferedWriter pw;
            BufferedReader in = null;
            try {
                URL text = new URL(url);
                HttpURLConnection http = (HttpURLConnection) text.openConnection();
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Accept", "application/json");
                http.setConnectTimeout(10000);
                http.setReadTimeout(10000);
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                pw = new BufferedWriter(outStream);
                pw.write(jsonObject.toString());
                pw.flush();

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
