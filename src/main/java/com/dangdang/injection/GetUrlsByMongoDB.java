package com.dangdang.injection;

import java.net.UnknownHostException;

import com.dangdang.ddframework.dbutil.MongoDBUtil;
import com.mongodb.MongoException;

public class GetUrlsByMongoDB {
	static String hostIp = "10.5.38.54";
	static String dbName = "temp";
	static String tableName = "click_bak_160515";
	static int port = 27017 ;
	
	public static void conn() throws UnknownHostException, MongoException{
		MongoDBUtil.connect(hostIp, port, dbName, tableName);
	}
	
	public static void getResultByAction() throws Exception{
		conn();
		String s = MongoDBUtil.findOne("getMedia");
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception{
		getResultByAction();
	}

}
