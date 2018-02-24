package com.plassey.httpclient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuickStartGet {

	/**
	 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static final String period_1hour = "60min";
	public static final String period_1day = "1day";
	public static final Long dayMs = 24l * 3600l * 1000l;
	public static final Long hourMs = 3600l * 1000l;
	public static final int size = 30;
	
	public static String period = "";
	public static Long currentMs = 0l;

	public static void main(String[] args) {

		String symbol = "ethusdt";
		period = period_1day;
		
		if(period_1hour.equals(period)) {
			currentMs = hourMs;
		}else if (period_1day.equals(period)) {
			currentMs = dayMs;
		}

		kline(period, size, symbol);
		ticker(symbol);
	}

	public static String httpGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseString = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				responseString = EntityUtils.toString(entity, "utf-8");
				EntityUtils.consume(entity);

			} finally {
				response.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseString;
	}

	public static void checkResultKLine(String responseString) {
		List<Double> lowList = new ArrayList<Double>();
		List<Double> highList = new ArrayList<Double>();
		double lowest = 9999999.9d, highest = 0.0d;
		Date lowestDate = null, highestDate = null;
		JSONObject responseJson = new JSONObject(responseString);
		Long ts = responseJson.getLong("ts");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("######00.00");
		JSONArray datas = responseJson.getJSONArray("data");
		System.out.println(responseString);
		System.out.println("id ts                   low   high  diff  count     vol");

		for (int i = 0; i < datas.length(); i++) {
			JSONObject data = datas.getJSONObject(i);
			Long diffLong = 1l * currentMs * i;
			Long tsMs = ts - diffLong;
			Date tsTmp = new Date(tsMs);
			double low = data.getDouble("low");
			double high = data.getDouble("high");
			double diff = high - low;
			double count = data.getDouble("count");
			double vol = data.getDouble("vol");
			lowList.add(low);
			highList.add(high);

			if(low < lowest) {
				lowest = low;
				lowestDate = tsTmp;				
			}
			if(high > highest) {
				highest = high;
				highestDate = tsTmp;
			}
			
			if (i + 1 < 10) {
				System.out.println(i + 1 + "  " + dateFormat.format(tsTmp) + "; " + decimalFormat.format(low) + "; "
						+ decimalFormat.format(high) + "; " + decimalFormat.format(diff) + "; "
						+ decimalFormat.format(count) + "; " + decimalFormat.format(vol));
			} else {
				System.out.println(i + 1 + " " + dateFormat.format(tsTmp) + "; " + decimalFormat.format(low) + "; "
						+ decimalFormat.format(high) + "; " + decimalFormat.format(diff) + "; "
						+ decimalFormat.format(count) + "; " + decimalFormat.format(vol));
			}
		}
		Collections.sort(lowList);
		Collections.sort(highList);
		System.out.println("result 1: lowest is " + lowest + " on " + dateFormatYMD.format(lowestDate) + ", highest is " + highest + " on " + dateFormatYMD.format(highestDate) + ", diff is " + (highest - lowest));
		System.out.println("result 2: mid lowList is " + lowList.get(lowList.size()/2) + ", "+ lowList);
		System.out.println("result 3: mid highList is " + highList.get(highList.size()/2) + ", " + highList);
		System.out.println("");
	}
	
	public static void checkResultTicker(String responseString) {
		JSONObject responseJson = new JSONObject(responseString);
		Long ts = responseJson.getLong("ts");
		Date tsDate = new Date(ts);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject tick = responseJson.getJSONObject("tick");
		System.out.println(responseString);
		System.out.println(dateFormat.format(tsDate));
		System.out.println("ask " + tick.getJSONArray("ask"));
		System.out.println("bid " + tick.getJSONArray("bid"));
		System.out.println("");
	}
	
	public static void kline(String period, int size, String symbol) {
		String url = "https://api.huobi.pro/market/history/kline?period=" + period + "&size=" + size + "&symbol=" + symbol;
		String responseString = httpGet(url);
		checkResultKLine(responseString);		
	}	
	public static void ticker(String symbol) {
		String url = "https://api.huobi.pro/market/detail/merged?symbol=" + symbol;
		String responseString = httpGet(url);
		checkResultTicker(responseString);		
	}
}