package com.dangdang.autotest.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.util.ResourceLoader;

/*
 * 原创项目配置相关功能
 * */
public class Config extends ConfigCore {
	

	protected static  String BaseUrl = "";
	protected static  String LoginUrl = "";
	protected static  String tempUrl = "";
	public static String getTempUrl() {
		return tempUrl;
	}



	protected static Logger logger = Logger.getLogger(Config.class);
	
	
	protected static Map<String, String> CommonParam =new HashMap<String, String>();
	public static String YCDBConfig="";
	public static String ECMSDBConfig="";
	public static String ACCOUNTDBConfig="";
	public static String UCENTERDBConfig="";
	
	//接口地址
		static{
			Init();
		}
	
	public static Map<String, String> getCommonParam() {
		return CommonParam;
	}

	/*
	 * 此处在用例开始运行前读取配置文件信息
	 */
	public static void Init(){
		try{
			

			//PropertyConfigurator.configure(ResourceLoader.loadCurrentPropertyFile("conf/log4j.properties"));
			
			//不同的环境读取不同环境的配置文件。
			Properties proConfig =ResourceLoader.loadCurrentPropertyFile("conf/"+testData+"/config-"+environment.name().toLowerCase()+".properties");
			BaseUrl = (String)proConfig.getProperty("baseUrl");
			LoginUrl = (String)proConfig.getProperty("loginUrl");
			tempUrl= (String)proConfig.getProperty("tempUrl");
			
			if(environment == TestEnvironment.TESTING){
				YCDBConfig="conf/"+testData+"/db/yc_db.cfg.xml";//proConfig.getProperty("yc_db");
				ECMSDBConfig="conf/"+testData+"/db/ecmstdb.cfg.xml";//proConfig.getProperty("ecmst_db");
				ACCOUNTDBConfig="conf/"+testData+"/db/accountdb.cfg.xml";//proConfig.getProperty("account_db");
				UCENTERDBConfig= "conf/"+testData+"/db/ucenterdb.cfg.xml";
			}
			else {
				YCDBConfig= "conf/"+testData+"/db/websql_yc.properties";
				ECMSDBConfig= "conf/"+testData+"/db/websql_ddreader.properties";
				ACCOUNTDBConfig= "conf/"+testData+"/db/websql_account.properties";
			}
			
			//proConfig.load(new FileInputStream("conf/config.properties"));
		
			
			
			String deviceString = device.toString().toLowerCase();

			//获取公共参数
			Properties commonParamConfig = null;
			if(testData.toLowerCase().contains("reader")){
				commonParamConfig=ResourceLoader.loadCurrentPropertyFile("conf/reader_common_param_"+deviceString+".properties");
			}
			else {//其他项目参数解析，有其他项目时再解析
				
			}
		
			CommonParam = new HashMap<String, String>((Map)commonParamConfig);
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
	
	public Config() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.print(Config.BaseUrl);
	}
}
