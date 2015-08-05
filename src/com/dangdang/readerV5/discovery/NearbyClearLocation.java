package com.dangdang.readerV5.discovery;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2Base;

public class NearbyClearLocation extends FixtureBase {
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2Base reponseResult = JSONObject.parseObject(result.toString(), ReponseV2Base.class);
		if(reponseResult.getStatus().getCode() == 0){	
			Assert.assertTrue(result.toString().contains("\"msg\":\"done\""));
			super.dataVerify();			
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
    
}
