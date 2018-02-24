package com.plassey.cryptocurrency;

public class XRPCalculator2 {
	public static double totalPriceUSD = 3.2596*15.33 + 1.6*81.38 + 1.85*28.73;
	public static double totalPriceCNY = totalPriceUSD*6.5;
	public static double quantityXRP = 15.33 + 81.38 + 28.73;
	public static double avgPrice = totalPriceUSD/quantityXRP;

	public static void main(String[] args) {
		
		System.out.println("totalPriceUSD " + totalPriceUSD);
		System.out.println("totalPriceCNY " + totalPriceCNY);
		System.out.println("quantityXRP   " + quantityXRP);
		System.out.println("avgPrice      " + avgPrice);

	}
}
