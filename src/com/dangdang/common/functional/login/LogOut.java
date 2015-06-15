package com.dangdang.common.functional.login;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

public class LogOut extends FunctionalBaseEx{
	
	ReponseV2Base reponseResult;
	
	


	public LogOut(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		URL=Config.getLoginUrl();
		addAction("logout");
	}
	
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult result = ParseParamUtil.parseParam(paramMap);
		login=result.getLogin();
		
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2Base>(){});
	}
	
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		super.dataVerify();
	}
	
	
	public ReponseV2Base getReponseResult() {
		return reponseResult;
	}
}
