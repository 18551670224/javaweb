package com.plassey.cryptocurrency;

public class Calculator {

	public static double sellPrice = 120000;
	public static double buyPrice  = 107587;
	public static double quantity = 0.06971083;
	public static double profit = (sellPrice - buyPrice) * quantity;
	public static double rate = profit / ( buyPrice * quantity) * 100;

	public static void main(String[] args) {
		System.out.println("profit is " + profit);
		System.out.println("rate is " + rate + "%");

	}
	

}
