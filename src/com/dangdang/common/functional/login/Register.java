package com.dangdang.common.functional.login;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.Data;
import com.dangdang.reader.functional.reponse.LoginReponse;

public class Register extends FunctionalBaseEx{
	
	LoginInfo	loginInfo;
	ReponseV2<Data> reponseResult;
	
	public Register(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		loginInfo = JSONObject.parseObject(JSONObject.toJSONString(param),LoginInfo.class);
		init(loginInfo);
	}
	
	/*
	 * 根据LoginInfo生成参数
	 */
	protected void init(LoginInfo loginInfo){
		paramMap=(Map<String, String>) JSONObject.toJSON(loginInfo);
		originalParamMap.putAll(paramMap);
		paramMap.putAll(Config.getCommonParam());
		addAction("register");
		URL=Config.getLoginUrl();
	}
	
	
	public Register(LoginInfo loginInfoArg){
		loginInfo =loginInfoArg;
		init(loginInfo);
	} 
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
}
