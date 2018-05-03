package com.nvwa.framework.service.psn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawlerDemo2 {
	 
	private ExecutorService pool = Executors.newFixedThreadPool(5);
	
    public static void main(String[] args) {
    	long time = System.nanoTime();
    	try {
			ExcelUtils.init(Constants.finalXlsxPath);
		} catch (Exception e) { 
			e.printStackTrace();
		}
        WebCrawlerDemo2 webCrawlerDemo = new WebCrawlerDemo2();
        webCrawlerDemo.myPrint(Constants.psn_jp);
       long end = System.nanoTime();
       System.out.println((end - time)/1000000 + "ms");
    }
 
    public void myPrint(String baseUrl) { 
        Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>(); // 存储链接-是否被遍历
                                                                            // 键值对
        String oldLinkHost = "";  //host 
        Pattern p = Pattern.compile("(https?://)?[^/\\s]*"); //比如：http://www.zifangsky.cn
        Matcher m = p.matcher(baseUrl);
        if (m.find()) {
            oldLinkHost = m.group();
        }
 
        oldMap.put(baseUrl, false);
        oldMap = crawlLinks(oldLinkHost, oldMap); 
        for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
				System.out.println("链接：" + mapping.getKey());  
        } 
        pool.shutdown();
 
    }
 
    /**
     * 抓取一个网站所有可以抓取的网页链接，在思路上使用了广度优先算法
     * 对未遍历过的新链接不断发起GET请求，一直到遍历完整个集合都没能发现新的链接
     * 则表示不能发现新的链接了，任务结束
     * 
     * @param oldLinkHost  域名，如：
     * @param oldMap  待遍历的链接集合
     * 
     * @return 返回所有抓取到的链接集合
     * */
    private Map<String, Boolean> crawlLinks(String oldLinkHost,
            Map<String, Boolean> oldMap) {
        Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();
        String oldLink = "";
        
        for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
            // System.out.println("link:" + mapping.getKey() + "--------check:"  + mapping.getValue());
            // 如果没有被遍历过
            if (!mapping.getValue()) {
                oldLink = mapping.getKey();
                if(oldLink.indexOf("/product/")!=-1) {
                	oldMap.replace(oldLink, false, true);
                	continue;
                }
                if(oldLink.indexOf("/resolve/")!=-1) {
                	oldMap.replace(oldLink, false, true);
                	continue;
                }
                // 发起GET请求
                try {
                   
                    Document doc = Jsoup.connect(oldLink).timeout(30 * 1000).get();
                    Elements elements = doc.select("a[href]");
                    for(Element e : elements) {
                    	String newLink = e.attr("href");    
                        // 判断获取到的链接是否以http开头
                        if (!newLink.startsWith("http")) {
                            if (newLink.startsWith("/"))
                                newLink = oldLinkHost + newLink;
                            else
                                newLink = oldLinkHost + "/" + newLink;
                        }
                        //去除链接末尾的 /
                        if(newLink.endsWith("/"))
                            newLink = newLink.substring(0, newLink.length() - 1);
                        //去重，并且丢弃其他网站的链接
                        if (!oldMap.containsKey(newLink)
                                && !newMap.containsKey(newLink)
                                && newLink.startsWith(oldLinkHost)) {
                            // System.out.println("temp2: " + newLink);
                            newMap.put(newLink, false);
                        } 
                    } 
                    oldMap.replace(oldLink, false, true); 
                    Task task = new Task(oldLink, doc);
                    pool.submit(task); 
                }catch (Exception e) {
                	 System.out.println(oldLink +":" +  e.getLocalizedMessage());
                } 
            }
        }
        //有新链接，继续遍历
        if (!newMap.isEmpty()) {
            oldMap.putAll(newMap);
            oldMap.putAll(crawlLinks(oldLinkHost, oldMap));  //由于Map的特性，不会导致出现重复的键值对
        }
        return oldMap;
    }
 
}