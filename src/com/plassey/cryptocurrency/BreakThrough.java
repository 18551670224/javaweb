package com.plassey.cryptocurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.plassey.httpclient.HttpGetUtil;

public class BreakThrough {
	
	public static final String symbol = "btcusdt";
	public static final String period = "1day";
	public static final String size = "21";

	public static void main(String[] args) {
		String url = "https://api.huobi.pro/market/history/kline?period=1day&size=21&symbol=btcusdt";
		String result = HttpGetUtil.kline(url);
		List<Node> nodeList = resultStringToList(result);
		checkBreakThrough20D(nodeList);
	}

	public static List<Node> resultStringToList(String responseString) {
		JSONObject responseJson = new JSONObject(responseString);
		JSONArray datas = responseJson.getJSONArray("data");
		String ch = responseJson.getString("ch");
		List<Node> nodeList = new ArrayList<Node>();
		for (int i = 0; i < datas.length(); i++) {
			Node node = new Node();
			JSONObject data = datas.getJSONObject(i);
			node.setId(data.getLong("id")*1000);
			node.setLow(data.getBigDecimal("low"));
			node.setHigh(data.getBigDecimal("high"));
			node.setCh(ch);
			nodeList.add(node);
		}
		Collections.reverse(nodeList);
		return nodeList;
	}
	
	public static void checkBreakThrough20D(List<Node> nodeList) {
		Node lowestNode = new Node();
		Node highestNode = new Node();
		lowestNode.setLow(new BigDecimal("9999999"));
		highestNode.setHigh(new BigDecimal("0"));
		for (int i = 0; i < nodeList.size() - 1; i++) {
			Node node = nodeList.get(i);
			if (node.getHigh().compareTo(highestNode.getHigh()) > 0) {
				highestNode = node;
			}
			if (node.getLow().compareTo(lowestNode.getLow()) < 0) {
				lowestNode = node;
			}
		}
		if (nodeList.get(nodeList.size() - 1).getLow().compareTo(lowestNode.getLow()) < 0) {
			System.out.println("downward break through occured, lowest is " + nodeList.get(nodeList.size() - 1));
		}
		if (nodeList.get(nodeList.size() - 1).getHigh().compareTo(highestNode.getHigh()) > 0) {
			System.out.println("upward break through occured, highest is " + nodeList.get(nodeList.size() - 1));
		}
	}
}
