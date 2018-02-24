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

public class HttpGetUtil {

	/**
	 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static final String symbol = "btcusdt";
	public static final String period = "1day";
	public static final String size = "30";
	public static final Long dayMs = 24l * 3600l * 1000l;
	public static final Long hourMs = 3600l * 1000l;
	public static Long currentMs = 0l;

	public static void main(String[] args) {
		kline(period, size, symbol);
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

	
	public static String kline(String period, String size, String symbol) {
		String url = "https://api.huobi.pro/market/history/kline?period=" + period + "&size=" + size + "&symbol=" + symbol;
		//String url = "https://api.huobi.pro/market/history/kline?period=" + period + "&symbol=" + symbol;
		String responseString = httpGet(url);
		return responseString;
	}
	
	public static String kline(String url) {
		//url = "https://api.huobi.pro/market/history/kline?period=1day&size=20&symbol=btcusdt";
		String responseString = httpGet(url);
		return responseString;
	}	
}