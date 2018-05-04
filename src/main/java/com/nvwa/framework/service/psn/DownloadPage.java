package com.nvwa.framework.service.psn;
  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class DownloadPage {

	private final String HOME = "G:/webpage"; 
	public List<Item> getItems() {
		return items;
	}

	private List<Item> items = new ArrayList<Item>();
	public static void main(String[] args) throws IOException {
		
		//String url = "https://store.playstation.com/zh-hant-hk/grid/STORE-MSF86012-SPECIALOFFER/0";
		String url = "https://store.playstation.com/zh-hant-hk/grid/STORE-MSF86012-PLUS_DIS_CONTENT/1";
		Document doc = Jsoup.connect(url).get(); 
		try { 
			List<Item> rs = DownloadPage.print(doc);
			ExcelUtils.deleteExcel(Constants.get(Constants.HK).XLSX_PATH);
			ExcelUtils.createExcel(Constants.get(Constants.HK).XLSX_PATH, Constants.get(Constants.HK).PSN);
	        ExcelUtils.writeExcel(rs, Constants.get(Constants.HK).XLSX_PATH); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static List<Item> print(Document doc) {
		List<Item> ret = new ArrayList<Item>();
		Elements elements = doc.getElementsByClass("grid-cell__body"); 
		 for(Element one : elements) { 
			Item item = new Item();
			String title = ((Element)one.getElementsByClass("grid-cell__title").get(0)).text(); 
			String display_price;
			try {
				Element p = (Element)one.getElementsByClass("price-display__price").get(0); 
				display_price = p!= null ? p.text():null;
			} catch (Exception e) {
				Element p;
				try {
					p = (Element)one.getElementsByClass("grid-cell__ineligible-reason").get(0);
					display_price = p!= null ? p.text():null;;
				} catch (Exception e1) { 
					display_price="NA";
				} 
				
			}
			 
			String psplus_price;
			try {
				Element a1 = (Element)one.getElementsByClass("price-display__price--is-plus-upsell").get(0); 
				psplus_price = a1!= null ? a1.text():null;
			} catch (Exception e) { 
				psplus_price = "";
			}  
			String strike_price;
			try {
				Element a2 = (Element)one.getElementsByClass("price-display__strikethrough").get(0); 
				strike_price = a2!= null ? a2.text():null;
			} catch (Exception e) {
				strike_price = "";
			} 
			 
			String classification;
			try {
				Element c = ((Element)one.getElementsByClass("grid-cell__left-detail grid-cell__left-detail--detail-2").get(0)); 
				classification = c != null ? c.text():null;
			} catch (Exception e) {
				classification = "";
			}
			
			String platform;
			try {
				Element b = ((Element)one.getElementsByClass("grid-cell__left-detail grid-cell__left-detail--detail-1").get(0)); 
				platform = b != null ? b.text():null;
			} catch (Exception e) {
				platform = "";
			}
			String hypelink = ((Element)one.selectFirst("a")).attr("href"); 
			item.setTitle(title);
			item.setDisplay_price(display_price);
			item.setPlatform(platform);
			item.setStrikethrough_price(strike_price);
			item.setPlus_price(psplus_price);
			item.setClassification(classification);
			item.setHypelink(hypelink);
			ret.add(item);
		} 
		 return ret;
	}

	private void getText(Document doc) throws IOException {
		//Document doc = Jsoup.connect(url).get();
		String title = "y";//doc.title();
		String filename = new StringBuffer( this.HOME + File.separator + title + ".txt").toString();
		FileWriter ps = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(ps);
		bw.write(doc.html());
		bw.close(); 
	}
	
	public void printExcel(Document doc) {
		this.print(doc);
		ExcelUtils.writeExcel(items, this.HOME+File.separator+"tst" + ".xlsx");
	}
} 
