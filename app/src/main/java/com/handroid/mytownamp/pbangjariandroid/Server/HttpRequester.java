package com.handroid.mytownamp.pbangjariandroid.Server;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequester {
	
	HttpTask http;

	public void request(String url, HttpCallback callback) {
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
			String postData = "";
			PrintWriter pw = null;
			BufferedReader in = null;
			
			//add~~~~~~~~~~~~~~
			try {
				URL text=new URL(url);
				HttpURLConnection http=(HttpURLConnection)text.openConnection();
				http.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
				http.setConnectTimeout(10000);
				http.setReadTimeout(10000);
				http.setRequestMethod("GET");
				http.setDoInput(true);
				//http.setDoOutput(true);


				in=new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
				StringBuffer sb=new StringBuffer();
				String inputLine;
				while((inputLine = in.readLine()) != null){
					sb.append(inputLine);
				}
				response=sb.toString();


			}catch (IOException e){
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
