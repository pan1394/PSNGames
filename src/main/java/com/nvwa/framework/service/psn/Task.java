package com.nvwa.framework.service.psn;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.nodes.Document;

public class Task implements Runnable {

	private String url;
	private Document doc;
	private Workbook workBook;
	
	public Task(String url, Document doc) {
		this.url = url;
		this.doc = doc;
	}
	
	public Task(String url, Document doc, Workbook workBook) {
		this(url,doc);
		this.workBook = workBook;
	}
	@Override
	public void run() {
		  try { 
			  List<Item> items = DownloadPage.print(doc);
			  if(items.size() == 0) {
				  System.out.println("no items in this page: " + url);
			  }else {
				  // ExcelUtils.writeExcel(items, Constants.finalXlsxPath);
				  ExcelUtils.writeExcel(items, workBook);
			  }
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
