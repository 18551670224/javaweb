package com.plassey.cryptocurrency;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Node {
	private Long id;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal amount;
	private BigDecimal vol;
	private BigDecimal count;
	private String ch;
	
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getVol() {
		return vol;
	}
	public void setVol(BigDecimal vol) {
		this.vol = vol;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	@Override
	public String toString() {
		Date date = new Date(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return "id = " + id + "(" + sdf.format(date) + ")"+ ", low = " + low + ", high = " + high + ", ch = " + ch;
	}

}
