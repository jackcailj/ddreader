package com.dangdang.readerV5.bookstore;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ColumnReponse;

import digital.BookStore5CommSQL;
import fitnesse.slim.SystemUnderTest;

/**
 * 
 * @author guohaiying
 *
 */
public class Column extends FixtureBase{

	ReponseV2<ColumnReponse> reponseResult;
	
	@SystemUnderTest
	public BookStore5CommSQL service = new BookStore5CommSQL();

	public Column(){
		URL = Config.getUrl();
	}
	
	public void jsonToClass(){
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ColumnReponse>>(){});
		System.out.println(reponseResult.getData().getSaleList().get(0).getSaleId());
	}
	
	//验证结果
	@Override
	public void dataVerify(){
		//验证扣款是否正确
		//验证已购列表中是否有此书
		//验证权限是否下放
		
	}
	
	public boolean resultVerify(){
		return true;
	}
	
	public boolean dbVerify(){
		return true;
	}
	
	public boolean getResult1(){
		//return reponseResult.getData().getSaleList().get(0).getPrice().toString();
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		Config.setBaseUrl("http://10.255.223.149/media/api.go");
		Map<String, String> map = new HashMap<String, String>();
		map.put("columnType","np_nslj");
		map.put("start","0");
		map.put("end","3");
		map.put("isFull","1");
		Column column = new Column();
		column.setParameters(map);
		//column.doGet("column");
	}
	
}
