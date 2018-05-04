package com.nvwa.framework.service.psn;

import java.text.NumberFormat;

public class Item {

	private String display_price;
	private String strikethrough_price;
	private String plus_price;
	private String title;
	private String classification;
	private String platform;
	private String hypelink;
	 
	
	public String getDisplay_price() {
		return display_price;
	}
	public void setDisplay_price(String display_price) {
		this.display_price = display_price;
	}
	public String getStrikethrough_price() {
		return strikethrough_price;
	}
	public void setStrikethrough_price(String strikethrough_price) {
		this.strikethrough_price = strikethrough_price;
	}
	public String getPlus_price() {
		return plus_price;
	}
	public void setPlus_price(String plus_price) {
		this.plus_price = plus_price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getHypelink() {
		return "https://store.playstation.com"+hypelink;
	}
	public void setHypelink(String hypelink) {
		this.hypelink = hypelink;
	} 
	
	public String getDiscount() {
		//display_price/strikethrough_price
		String discount;
		try {
			float res = this.getDollar(this.display_price)/this.getDollar(this.strikethrough_price);
			res = res * 10;
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(1);
			discount = format.format(res) + "折";
		} catch (Exception e) {
			discount = "";
		}
		return discount;
	}
	public String getPlus_discount() {
		//plus_price/display_price
		String discount;
		try {
			float res = this.getDollar(this.plus_price)/this.getDollar(this.display_price);
			res = res * 10;
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(1);
			discount = format.format(res) + "折";
		} catch (Exception e) {
			discount = "";
		}
		return discount; 
	}
	
	private float getDollar(String price) {
		if(price.indexOf(Constants.CURRENCY_HK) != -1)
			return Float.parseFloat(price.substring(3));
		else if(price.indexOf(Constants.CURRENCY_JP) != -1 || price.indexOf(Constants.CURRENCY_US) != -1) {
			return Float.parseFloat(price.substring(1));
		}
			throw new RuntimeException("Not a float.");
	 
	}
}
