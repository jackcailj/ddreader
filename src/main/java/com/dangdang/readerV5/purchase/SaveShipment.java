package com.dangdang.readerV5.purchase;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Data;

public class SaveShipment extends FixtureBase{
	String cartId;
	
	public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(login!=null&&cartId==null){
			cartId = login.getCustId();
		}
		if(!(paramMap.get("cartId").isEmpty())){
			paramMap.put("cartId",cartId);
		}
		
	}
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<Data> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
