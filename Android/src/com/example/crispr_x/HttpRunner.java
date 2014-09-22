package com.example.crispr_x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONObject;

import android.util.Log;

public class HttpRunner{

	static InputStream is = null;
	static JSONObject jObj = null;
	static String jsonString = "";

	// constructor
	public HttpRunner() {}

	//get数据
	public String makeHttpGET(String url) {
		// Making HTTP request
		Log.d("http请求", url);
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// 请求超时,2*60 * 1000秒
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2*60 * 1000);
			HttpGet httpRequest = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpRequest);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jsonString = "ERROR:"+e.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			jsonString = "ERROR:"+e.toString();
		} catch (IOException e) {
			e.printStackTrace();
			jsonString = "ERROR:"+e.toString();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			jsonString = sb.toString();
			Log.d("json数据", jsonString);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		
		// return JSON String
		return jsonString;
	}
	
	//post数据
		public String makeHttpPOST(String url,List<NameValuePair> params) {
			// Making HTTP request
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// 请求超时,10秒
				httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();
				jsonString = sb.toString();
				Log.d("json数据", jsonString);
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			
			// return JSON String
			return jsonString;
		}
		
}
