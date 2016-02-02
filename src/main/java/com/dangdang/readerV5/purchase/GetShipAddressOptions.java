package com.dangdang.readerV5.purchase;

import test.user;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.AddResults;
import com.dangdang.readerV5.reponse.BarDefImgResponse;

public class GetShipAddressOptions extends FixtureBase{
	
	public ReponseV2<AddResults> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AddResults>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<AddResults> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			reponseResult.setData(new AddResults());
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
