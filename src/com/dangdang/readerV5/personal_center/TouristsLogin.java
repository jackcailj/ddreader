package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.reponse.TouristsLoginReponse;
import com.dangdang.ddframework.reponse.ReponseV2;

import java.util.Map;

public class TouristsLogin extends FixtureBase{

	ReponseV2<TouristsLoginReponse> reponseResult;

	public TouristsLogin(){}

	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<TouristsLoginReponse>>(){});
	}
	
	public ReponseV2<TouristsLoginReponse> getReponseResult() {
		return reponseResult;
	}

	
}
