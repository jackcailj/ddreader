package com.dangdang.reader.functional.purchaseAndDownload;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FunctionalBaseEx;

public class GetShoppingCartId extends FunctionalBaseEx{
	
	public GetShoppingCartId(Map<String, String> param){
		super(param);
		if(paramMap.get("action")==null){
			paramMap.put("action", "getShoppingCartId");
		}
	}
	
	public GetShoppingCartId(String token){
		if(paramMap.get("action")==null){
			paramMap.put("action", "getShoppingCartId");
		}
		paramMap.put("token", token);
	}
	
	
	/*
	 * {
    "data": {
        "cartId": "1410271433249155",
        "currentDate": "2014-10-27 14:33:24",
        "systemDate": "1414391604097"
    },
    "status": {
        "code": 0
    },
    "systemDate": 1414391604096
}
	 */
	public String  getCardId() {
		JSONObject jsonObject =JSONObject.parseObject(result.toString());
		JSONObject data=(JSONObject) jsonObject.get("data");
		return data.get("cartId").toString();
	}
}
