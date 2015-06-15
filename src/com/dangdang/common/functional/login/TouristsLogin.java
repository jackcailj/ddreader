package com.dangdang.common.functional.login;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.reponse.TouristsLoginReponse;
import com.dangdang.ddframework.reponse.ReponseV2;

public class TouristsLogin extends FunctionalBaseEx{
	
	ReponseV2<TouristsLoginReponse> reponseResult;
	
	

	public TouristsLogin(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("touristsLogin");
	}
	
	
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
