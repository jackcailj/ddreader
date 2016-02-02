package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.PayPatten;

public class GetDiscountPattern extends FixtureBase{
	
	public ReponseV2<PayPatten> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PayPatten>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<PayPatten> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			reponseResult.setData(new PayPatten());
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
