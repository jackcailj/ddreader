package com.dangdang.reader.functional.personcenter;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;

public class GenerateOrder extends FunctionalBaseEx{
	ReponseV2<Map<String, Object>> reponseResult;
	
	public GenerateOrder(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		bHttps=true;
		URL=URL.replace("http", "https");
	}
	
	public GenerateOrder(String token,String productId) {
		// TODO Auto-generated constructor stub
		//生成depositOrderNo
		String buyString="action=multiActionV2&field={\"dependActions\":[{\"action\":\"appendCart\",\"params\":{\"productIds\":"+productId+",\"orderSource\":40000,\"from_url\":103,"
			    		+ "\"permanentId\":\"20150121071907937689102417004505362\",\"oneKeyBuy\":true},\"parseParams\":{\"cartId\":\"cartId\"}},{\"action\":\"getEbookOrderFlow\","
			    		+ "\"params\":{\"productIds\":"+productId+",\"orderSource\":40000,\"from_url\":103,\"permanentId\":\"20150121071907937689102417004505362\"},\"parseParams\":{\"cartId\":\"cartId\"}},"
			    		+ "{\"action\":\"saveEbookPayment\",\"params\":{\"orderSource\":40000,\"from_url\":103},\"parseParams\":{\"cartId\":\"cartId\"}},{\"action\":\"submitEbookOrder\","
			    		+ "\"params\":{\"orderSource\":40000,\"from_url\":103}}]}&returnType=json&deviceType=YC_Android&channelId=40000&clientVersionNo=1.0&serverVersionNo=1.2.1&permanentId=20150121071907937689102417004505362"
			    		+ "&deviceSerialNo=864504020721219&macAddr=cc%3Aa2%3A23%3Afb%3A2f%3A7d&resolution=1080*1920&clientOs=4.2.2&channelType=VP&token="+token;
			
		paramMap.putAll(Util.generateMap(buyString));
		bHttps=true;
		URL=URL.replace("http", "https");
		
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Map<String, Object>>>(){});
	}
	
	public String getOrderId() throws Exception
	{
		
		if(reponseResult.getStatus().getCode()==0){
			Map<String, Object> resultMap=(Map<String, Object>) reponseResult.getData().get("result");
			Map<String, Object> submitEbookOrderMap=(Map<String, Object>) resultMap.get("submitEbookOrder");
			Map<String, Object> submitEbookOrderDataMap=(Map<String, Object>) submitEbookOrderMap.get("data");
			Map<String, Object> submitEbookOrderDataResultMap=(Map<String, Object>) submitEbookOrderDataMap.get("result");
			
			return submitEbookOrderDataResultMap.get("order_id").toString();
			
		}
		else {
			throw new Exception("异常，返回值为:"+reponseResult.getStatus().getCode());
		}
	}
	
	public String getKey() throws Exception{
		if(reponseResult.getStatus().getCode()==0){
			Map<String, Object> resultMap=(Map<String, Object>) reponseResult.getData().get("result");
			Map<String, Object> submitEbookOrderMap=(Map<String, Object>) resultMap.get("submitEbookOrder");
			Map<String, Object> submitEbookOrderDataMap=(Map<String, Object>) submitEbookOrderMap.get("data");
			//Map<String, Object> submitEbookOrderDataResultMap=(Map<String, Object>) submitEbookOrderDataMap.get("result");
			
			return submitEbookOrderDataMap.get("key").toString();
			
		}
		else {
			throw new Exception("异常，返回值为:"+reponseResult.getStatus().getCode());
		}
	}
	
}
