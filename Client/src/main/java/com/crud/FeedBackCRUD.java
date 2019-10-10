package com.crud;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.pojoclass.FeedbackPOJO;

public class FeedBackCRUD {
	private static String URL = "http://localhost:8089/";
	public static void main(String[] args) {
		try {
			// SongCRUD.updateSong(body);
			// SongCRUD.deleteSong(5);
			// SongCRUD.getSongValues();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void post(String json) throws Exception {
		String query_url = URL + "feedback/add";
		try {
			URL url = new URL(query_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();
			InputStream in = new BufferedInputStream(conn.getInputStream());
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static List<FeedbackPOJO> getFB() throws Exception {
		URL obj = new URL(URL + "feedback/getall");
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = "";
		String output = "";
		StringBuffer respone = new StringBuffer();
		JSONArray jsonArray = null;
		while ((inputLine = in.readLine()) != null) {
			respone.append(inputLine);
			jsonArray = new JSONArray(respone.toString());
		}
		in.close();
		List<FeedbackPOJO> list = new ArrayList<FeedbackPOJO>();
		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(),FeedbackPOJO.class));
		}
		return list;
	}

	public static void updateFB(String body) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpPut = new HttpPut(URL + "feedback/update");
			httpPut.addHeader("Content-type", "application/json");
			httpPut.setEntity(new StringEntity(body, "UTF-8"));
			HttpResponse response2 = httpclient.execute(httpPut);
			HttpEntity respEntity = response2.getEntity();
//        	if(respEntity != null){
//        		String content =  EntityUtils.toString(respEntity);
//        		System.out.println(content);
//        	}
		}
	}

	public static void deleteFB(int id) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpDelete httpDelete = new HttpDelete(URL + "feedback/delete/" + id);
			httpDelete.getRequestLine();
			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			httpclient.execute(httpDelete, responseHandler);
		}
	}
}
