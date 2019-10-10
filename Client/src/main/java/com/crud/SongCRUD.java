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
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pojoclass.CategoryPOJO;
import com.pojoclass.SongPOJO;

public class SongCRUD {
	private static String URL = "http://localhost:8089/";
	public static void main(String[] args) {
		try {
			// SongCRUD.updateSong(body);
			// SongCRUD.deleteSong(5);
			// SongCRUD.getSongValues();
			//SongCRUD.getByID(51);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int sendPost(String json) throws Exception {
		String query_url = URL + "song/add";
		int i = 0;
		try {
			URL url = new URL(query_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			//i = Integer.valueOf(conn.getResponseMessage());
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();
			String inputLine = "";
			StringBuffer respone = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				respone.append(inputLine);
				i = Integer.valueOf(respone.toString());
			}
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(i);
		return i;
		
	}

	public static List<SongPOJO> getSongValues() throws Exception {
		URL obj = new URL(URL + "song/getall");
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
		List<SongPOJO> list = new ArrayList<SongPOJO>();
		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(), SongPOJO.class));
		}
		return list;
	}
	public static SongPOJO getByID(int id) throws Exception {
		URL obj = new URL(URL + "song/" + id);
		//String iDString = String.valueOf(id);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer respone = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			respone.append(inputLine);
		}
		in.close();
		JSONObject jObject = new JSONObject(respone.toString());
		//System.out.println(jObject);
		SongPOJO songPOJO = new SongPOJO(
				jObject.getInt("id"),
				jObject.getString("name"),
				jObject.getString("singer"),
				jObject.getString("musican"),
				jObject.getString("link"),
				jObject.getString("dateCreate"),
				jObject.getInt("idCategory"),
				jObject.getInt("idUser")
				);
		//System.out.println(categoryPOJO.getId() +":"+ categoryPOJO.getTitle());
		return songPOJO;
	}
	@SuppressWarnings("unused")
	public static void updateSong(String body) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpPut = new HttpPut(URL + "song/update");
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

	public static void deleteSong(int id) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpDelete httpDelete = new HttpDelete(URL + "song/delete/" + id);
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