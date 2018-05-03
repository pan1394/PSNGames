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
			  ExcelUtils.writeExcel(items, Constants.finalXlsxPath);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
