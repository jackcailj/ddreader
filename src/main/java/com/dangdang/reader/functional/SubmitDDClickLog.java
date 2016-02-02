package com.dangdang.reader.functional;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.dbutil.MongoDBUtil;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.Data;

public class SubmitDDClickLog extends FunctionalBaseEx{
	protected ReponseV2<Data> reponseResult;
	
	public SubmitDDClickLog(Map<String,String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("submitDDClickLog");		
	}
	
	public static String parseParam(Map<String,String> param){
		Iterator<Map.Entry<String,String>> iterator = param.entrySet().iterator();
		String paramStr="[{";		
		Date date = new Date();
		while(iterator.hasNext()){
			Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			if(!(key.equals("期望"))){
				String value = entry.getValue();	
				if(key.equals("\"actionTime\"")){
					value = Long.toString(date.getTime());
				}						
				paramStr += key +":" + (value.isEmpty()?"\"\"":value)+",";
				iterator.remove();
			}
			
		}		
		return  paramStr.substring(0, paramStr.length()-1) + "}]";	
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		result = HttpDriver.doGet(URL, paramMap);
	}
	
	public ReponseV2<Data> getResult() {
		if(reponseResult==null){
			reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
		}
		return reponseResult;
	}
	
	@Override
	public void doWorkAndVerify() throws Exception {
		int before = MongoDBUtil.getAllCount();
		doWork();
		Thread.sleep(2000);
		if(getResult().getStatus().getCode().toString().equals("0")){
			int after = MongoDBUtil.getAllCount();
			Assert.assertEquals(after, before+1);
		}
	}


}
