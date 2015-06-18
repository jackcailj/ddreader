package com.dangdang.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.util.ResourceLoader;

public class Config extends ConfigCore{
	protected static Logger logger = Logger.getLogger(Config.class);
	
	protected static  String BaseUrl = "";
	protected static  String LoginUrl = "";
	protected static  String tempUrl = "";	
	
	public static String YCDBConfig="";
	public static String ECMSDBConfig="";
	public static String ACCOUNTDBConfig="";
    public static String UCENTERDBConfig="";
	
	protected static Map<String, String> CommonParam =new HashMap<String, String>();
		
	public Config() {
		// TODO Auto-generated constructor stub
	}
	
	public void setCommParam(Map<String, String> params){
		CommonParam=params;
	}
	
	static{
		Init();
	}
	
	public static void Init(){
		
		PropertyConfigurator.configure(ResourceLoader.loadCurrentPropertyFile("conf/log4j.properties"));
		try{
			if(getEnvironment() == TestEnvironment.TESTING){
				YCDBConfig="conf/db/yc_db.cfg.xml";
				ECMSDBConfig="conf/db/ecmstdb.cfg.xml";
				ACCOUNTDBConfig="conf/db/accountdb.cfg.xml";
                UCENTERDBConfig="conf/db/ucenterdb.cfg.xml";
			}
			else {
				YCDBConfig="conf/websql_yc.properties";
				ECMSDBConfig="conf/websql_ddreader.properties";
				ACCOUNTDBConfig="conf/websql_account.properties";
			}					

		}
		catch(Exception e)
		{
			logger.info(e);
		}
		
	}
		
	public static String getUrl() {
		return BaseUrl;
	}
	
	public static  String getLoginUrl() {
		return LoginUrl;
	}
	
	public static String getBaseUrl() {
		return BaseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		BaseUrl = baseUrl;
	}	

	public static void setLoginUrl(String loginUrl) {
		LoginUrl = loginUrl;
	}

	public static void setTempUrl(String tempUrl) {
		Config.tempUrl = tempUrl;
	}

	public static String getTempUrl() {
		return tempUrl;
	}	
	
	public static Map<String, String> getCommonParam() {
		return CommonParam;
	}
	
	public static void setCommonParam(Map<String, String> commonParam) {
		CommonParam = commonParam;
	}
		
	public static void main(String[] args) {
		
		System.out.print(Config.BaseUrl);
	}

}
