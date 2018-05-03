package com.nvwa.framework.service.sample;

import java.util.Arrays;
import java.util.List;

import com.nvwa.framework.dataaccess.QueryExecutor;
import com.nvwa.framework.dataaccess.QueryExecutorFactory;

public class Greeting {

	public String hello(String param) {
		System.out.println("==================================================================================================");
		System.out.println("This is a simple Nvwa framework service. Once you saw it, it means you get the service from server.");
		System.out.println("you're calling method com.nvwa.frameowrk.service.sample.Greeting.hello(String param).");
		System.out.printf("what you typed method param: %s", param);
		System.out.println();
		System.out.println("==================================================================================================");
		//this.query(1);
		return "Hello World, " + param;
	}
	
	
	public String longService(String param) {
		System.out.println("==================================================================================================");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) { 
		}
		System.out.println("This service consumes 10 seconds"); 
		System.out.println();
		System.out.println("==================================================================================================");
		//this.query(1);
		return "longService param: " + param;
	}
	
	public void query(String id) {
		System.out.println("==================================================================================================");
		QueryExecutor executor = QueryExecutorFactory.getQueryExecutor();
		List list = executor.selectList("select * from menu where menu_id=?", Arrays.asList(id));
		System.out.println(list);
		System.out.println("==================================================================================================");
	}
	 
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
	}
}
