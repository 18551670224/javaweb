package com.plassey.cryptocurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.plassey.httpclient.HttpGetUtil;

public class BreakThroughAll {
	
	public static String symbol = "btcusdt";
	public static final int period = 20;
	public static List<String> resultList = new ArrayList<String>();

	public static void main(String[] args) {
		resultList = new ArrayList<String>();
		symbol = "btcusdt";
		checkBreakThrough(symbol);

		symbol = "bchusdt";
		checkBreakThrough(symbol);

		symbol = "ethusdt";
		checkBreakThrough(symbol);
		
		symbol = "etcusdt";
		checkBreakThrough(symbol);
		
		symbol = "ltcusdt";
		checkBreakThrough(symbol);
		
		symbol = "eosusdt";
		checkBreakThrough(symbol);
				
		symbol = "xrpusdt";
		checkBreakThrough(symbol);
		
		symbol = "dashusdt";
		checkBreakThrough(symbol);
	}
	public static void main1() {
		resultList = new ArrayList<String>();
		
		symbol = "btcusdt";
		checkBreakThrough(symbol);

		symbol = "bchusdt";
		checkBreakThrough(symbol);

		symbol = "ethusdt";
		checkBreakThrough(symbol);
		
		symbol = "etcusdt";
		checkBreakThrough(symbol);
		
		symbol = "ltcusdt";
		checkBreakThrough(symbol);
		
		symbol = "eosusdt";
		checkBreakThrough(symbol);
				
		symbol = "xrpusdt";
		checkBreakThrough(symbol);
		
		symbol = "dashusdt";
		checkBreakThrough(symbol);
	}

	public static void checkBreakThrough(String symbol) {
		String url = "https://api.huobi.pro/market/history/kline?period=1day&size=30&symbol=" + symbol;
		String result = HttpGetUtil.kline(url);
		List<Node> nodeList = resultStringToList(result);
		checkBreakThroughAll(nodeList);
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
	
	public static void checkBreakThroughAll(List<Node> nodeList) {
		System.out.println(nodeList.size() + " " + nodeList.get(0).getCh());
		resultList.add(String.valueOf(nodeList.size()) + " " + nodeList.get(0).getCh());
		for (int i = period + 1; i < nodeList.size(); i++) {
			checkBreakThroughEach(nodeList.subList(i - period - 1, i));
		}
	}
	
	public static void checkBreakThroughEach(List<Node> nodeList) {
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
			resultList.add("downward break through occured, lowest is " + nodeList.get(nodeList.size() - 1));
		}
		if (nodeList.get(nodeList.size() - 1).getHigh().compareTo(highestNode.getHigh()) > 0) {
			System.out.println("upward break through occured, highest is " + nodeList.get(nodeList.size() - 1));
			resultList.add("upward break through occured, highest is " + nodeList.get(nodeList.size() - 1));
		}
	}
}
