package com.nvwa.framework.service.sample;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.nvwa.framework.communication.Request;
import com.nvwa.framework.communication.adapter.tcp2.client.MessageBase;

public class Singleton implements Closeable {
	private ExecutorService threadpool = null;

	private static class SingletonHolder {
		private static Singleton SINGLETON = new Singleton();;
	}
	
	private Singleton() { 
		threadpool = Executors.newFixedThreadPool(1);
	}
	
	private static Singleton getInstance() {
		return SingletonHolder.SINGLETON;
	}
	
	
	public <T> Future<T> async(Callable<T> task) { 
		return threadpool.submit(task);
	}
	
	 
	public void send() {
		client c = new client();
		 try {
			c.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class Math{
		
		int x;
		int y;
		
		Math(int x, int y){
			this.x =x;
			this.y =y;
		}
		int add() {
			return x+y;
		}
		
		int times() {
			return x*y;
		}
	}
	
	private Future<Integer> executeAdd() {
		Math a = new Math(1,2);
		return this.async(new Callable<Integer>() { 
			@Override
			public Integer call() throws Exception { 
				Thread.sleep(1000);
				return a.add();
			} 
		}); 
	}
	
	private Future<Integer> execute2() {
		Math a = new Math(1,2);
		return this.async(new Callable<Integer>() { 
			@Override
			public Integer call() throws Exception { 
				Thread.sleep(2000);
				return a.times();
			} 
		}); 
	}
	
	private class client{
		private Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		
		public client() {
			s = new Socket();
			SocketAddress address = new InetSocketAddress("192.168.83.1", 10002);
			try {
				s.connect(address, 3*1000);
				s.setTcpNoDelay(true);
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send() throws Exception {
			Request req = new Request();
			req.setServiceId("SAMPLE_SERVICE#query"); 
			req.setField("id", "M00900000");  
			MessageBase base = new MessageBase();
			base.setContent(req);  
			dos.write(base.getBytes());
			dos.flush(); 
		}
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		
		Singleton instance = Singleton.getInstance();
		instance.send();
		while(true) {
			
		}
		//instance.close();
	}

	@Override
	public void close() throws IOException {
		threadpool.shutdown();
		threadpool = null;
		//System.exit(0);
		
	}
}
