package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;

import java.util.Map;

public class GenerateOrder extends FixtureBase{
	ReponseV2<Map<String, Object>> reponseResult;
	
	public GenerateOrder() {
		// TODO Auto-generated constructor stub

		bHttps=true;
		URL=URL.replace("http", "https");
	}
	
	public GenerateOrder(String token, String productId) {
		// TODO Auto-generated constructor stub
		//生成depositOrderNo
		String buyString="action=multiAction&field={\"dependActions\":[{\"action\":\"appendCart\",\"params\":{\"productIds\":\""+productId+"\",\"oneKeyBuy\":\"true\",\"referType\":\"personal\",\"fromPlatform\":\"103\",\"orderSource\":\"50000\"},"+
		"\"parseParams\":{\"cartId\":\"cartId\"}},{\"action\":\"getOrderFlow\",\"params\":{\"productIds\":\""+productId+"\",\"fromPlatform\":\"103\",\"orderSource\":\"50000\"}},"+
	"{\"action\":\"savePayment\",\"params\":{\"fromPlatform\":\"103\",\"orderSource\":\"50000\"}},"+
	"{\"action\":\"submitOrder\",\"params\":{\"isChargeOrder\":\"1\",\"fromPlatform\":\"103\",\"isPaperBook\":\"false\",\"depositPayment\":\"1018\",\"orderSource\":\"50000\"}}]}";

		paramMap.put("token",token);
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
			Map<String, Object> submitEbookOrderMap=(Map<String, Object>) resultMap.get("submitOrder");
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
