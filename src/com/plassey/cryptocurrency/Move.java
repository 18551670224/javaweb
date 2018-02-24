package com.plassey.cryptocurrency;

public class Move {

	public static double inputCNY = 10000;
	public static double inputUSD = inputCNY/7.35;
	public static double buyUnitPriceUSD  = 16440;
	public static double buyBTC = inputUSD/buyUnitPriceUSD;
	public static double sellUnitPriceCNY = 120000;
	public static double sellTotalPrice = sellUnitPriceCNY*buyBTC;
	public static double profit = sellTotalPrice - inputCNY;
	public static double rate = profit/inputCNY;

	public static void main(String[] args) {
		System.out.println("profit is " + profit);
		System.out.println("rate is " + rate + "%");
	}
	

}
