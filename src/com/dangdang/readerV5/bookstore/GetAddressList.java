package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;

public class GetAddressList extends FixtureBase{
	ReponseV2<GetAddressListReponse> reponseResult;
	
	public void verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetAddressListReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
		}
	}
}
