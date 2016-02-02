package com.dangdang.reader.functional.bookshelf;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;

public class NearbyClearLocation extends FunctionalBaseEx {
	public NearbyClearLocation(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("nearbyClearLocation");
	} 
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		super.parseParam();
		ParseResult parseResult=ParseParamUtil.parseParam(paramMap);
		login = parseResult.getLogin();
		paramMap.remove("token");
		if(login!=null){
			paramMap.put("uToken", login.getToken());
		}
	}
	
	public ReponseV2<Data> getReponseResult() {
		return JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Data>>(){});
	}

}
