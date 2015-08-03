package com.dangdang.readerV5.discovery;

import java.util.List;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.NearbyList;
import com.dangdang.reader.functional.reponse.NearbySearchResponse;

public class NearbySearch extends FixtureBase{
	public ReponseV2<NearbySearchResponse> getResult() {
		return JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<NearbySearchResponse>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<NearbySearchResponse> reponseResult = getResult();
		if(reponseResult.getStatus().getCode()==0){
			List<NearbyList> list = reponseResult.getData().getNearbyList();
			if(list.size()==0){
				throw new Exception("没找到附近的人");
			}
			Assert.assertTrue(list.size() > 0, "附近人没找到");
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
