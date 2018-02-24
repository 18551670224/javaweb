package com.plassey.cryptocurrency;

public class XRPCalculator {
	public static double buyPriceCNY  = 1000;
	public static double rate = 7.7;
	public static double quantityUSDT = buyPriceCNY/rate;
	public static double XRPUnitPrice = 1.9;
	public static double quantityXRP = buyPriceCNY/rate/XRPUnitPrice;

	public static void main(String[] args) {
		
		System.out.println("quantityUSDT is " + quantityUSDT);
		System.out.println("quantityXRP is " + quantityXRP);

	}
}
