package com.crud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pojoclass.CategoryPOJO;

public class CategoryCRUD {
	private static String URL = "http://localhost:8089/";

	public static void main(String[] args) {
		try {
			//CategoryCRUD.getByID(2);
			//CategoryCRUD.getListCategory();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static CategoryPOJO getByID(int id) throws Exception {
		URL obj = new URL(URL + "category/" + id);
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
		CategoryPOJO categoryPOJO = new CategoryPOJO(jObject.getInt("idCategory"),jObject.getString("title"));
		//System.out.println(categoryPOJO.getId() +":"+ categoryPOJO.getTitle());
		return categoryPOJO;
	}
	
	public static Map<Integer, String> getListCategory() throws Exception {
		URL obj = new URL(URL + "category/getall");
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		// int responeCode = connection.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = "";
		StringBuffer respone = new StringBuffer();
		JSONArray jsonObject = null;
		while ((inputLine = in.readLine()) != null) {
			respone.append(inputLine);
			jsonObject = new JSONArray(respone.toString());
			// System.out.println(respone);
		}
		in.close();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < jsonObject.length(); i++) {
			map.put(jsonObject.getJSONObject(i).getInt("idCategory"), jsonObject.getJSONObject(i).getString("title"));
		}
		return map;
	}

	public static int sendPost(String json) throws Exception {
		int id = 0;
		try {
			URL url = new URL(URL + "category/add");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = "";
			StringBuffer respone = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				respone.append(inputLine);
				id = Integer.valueOf(respone.toString());
			}
			in.close();
			conn.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
		return id;
	}

	public static List<CategoryPOJO> getCategoryValues() throws Exception {
		URL obj = new URL(URL + "category/getall");
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = "";
		StringBuffer respone = new StringBuffer();
		JSONArray jsonArray = null;
		while ((inputLine = in.readLine()) != null) {
			respone.append(inputLine);
			jsonArray = new JSONArray(respone.toString());
		}
		in.close();
		List<CategoryPOJO> list = new ArrayList<CategoryPOJO>();
		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(), CategoryPOJO.class));
		}
		return list;
	}

	public static int getKeyByValue(Map<Integer, String> map, String value) {
		int key = 0;
		for (Entry<Integer, String> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				key = entry.getKey();
			}
		}
		return key;
	}

	public static void deleteCate(int id) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpDelete httpDelete = new HttpDelete(URL + "category/delete/" + id);
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

	public static void updateCate(String body) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpPut = new HttpPut(URL + "category/update");
			httpPut.addHeader("Content-type", "application/json");
			httpPut.setEntity(new StringEntity(body, "UTF-8"));
			// httpPut.getRequestLine();
			HttpResponse response2 = httpclient.execute(httpPut);
			HttpEntity respEntity = response2.getEntity();
//        	if(respEntity != null){
//        		String content =  EntityUtils.toString(respEntity);
//        		System.out.println(content);
//        	}
		}
	}
}
