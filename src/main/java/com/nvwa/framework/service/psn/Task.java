package com.nvwa.framework.service.psn;

import java.util.List;

import org.jsoup.nodes.Document;

public class Task implements Runnable {

	private String url;
	private Document doc;
	public Task(String url, Document doc) {
		this.url = url;
		this.doc = doc;
	}
	@Override
	public void run() {
		  try { 
			  List<Item> items = DownloadPage.print(doc);
			  if(items.size() == 0) {
				  System.out.println("no items in this page: " + url);
			  }else {
				  ExcelUtils.writeExcel(items, Constants.finalXlsxPath);
			  }
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
