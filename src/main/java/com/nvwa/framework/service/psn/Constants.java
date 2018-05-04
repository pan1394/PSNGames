package com.nvwa.framework.service.psn;

public class Constants {

	public static final String HOME_PATH = "G:/webpage/";
	public static final String PSN_HK = "PSN HK STORE";
	public static final String PSN_JP = "PSN JP STORE";
	public static final String PSN_US = "PSN US STORE";
	public static final String XLSX_PATH_HK = HOME_PATH +"psn_games_hk.xlsx";
	public static final String XLSX_PATH_JP = HOME_PATH +"psn_games_jp.xlsx";
	public static final String XLSX_PATH_US = HOME_PATH +"psn_games_us.xlsx";
	public static final String URL_US = "https://store.playstation.com/en-us/home/games";
	public static final String URL_HK = "https://store.playstation.com/zh-hant-hk/home/games";
	public static final String URL_JP = "https://store.playstation.com/ja-jp/home/games?SMCID=jGMpscomheader_storetop";
	public static final String HK = "hk";
	public static final String JP = "jp";
	public static final String US = "us";
	public static final String CURRENCY_HK = "HK$";
	public static final String CURRENCY_US = "$";
	public static final String CURRENCY_JP = "¥";
	
	public static Base get(String code) {
		return new Base(code);
	}
	
	public static class Base{
		
		String PSN;
	    String XLSX_PATH;
		String URL;
		
		public Base(String code) {
			if("hk".equalsIgnoreCase(code)) {
				PSN = Constants.PSN_HK;
				XLSX_PATH = Constants.XLSX_PATH_HK;
				URL = Constants.URL_HK;
			}
			else if("jp".equalsIgnoreCase(code)) {
				PSN = Constants.PSN_JP;
				XLSX_PATH = Constants.XLSX_PATH_JP;
				URL = Constants.URL_JP;
			}
			else if("US".equalsIgnoreCase(code)) {
				PSN = Constants.PSN_US;
				XLSX_PATH = Constants.XLSX_PATH_US;
				URL = Constants.URL_US;
			} 
		}
	}
}
