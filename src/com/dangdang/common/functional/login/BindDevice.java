package com.dangdang.common.functional.login;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.util.Util;

public class BindDevice  extends FunctionalBaseEx{

	/*
	 * 可以不包括action字段
	 */
	public BindDevice(Map<String, String> param) {
		
		BindInfo bindInfo = JSONObject.parseObject(JSONObject.toJSONString(param),BindInfo.class);
		init(bindInfo);
		
	}
	
	public BindDevice(BindInfo bindInfo){
		init(bindInfo);
	} 
	
	protected void init(BindInfo bindInfo){
		paramMap=(Map<String, String>) JSONObject.toJSON(bindInfo);
		originalParamMap.putAll(paramMap);
		paramMap.putAll(Config.getCommonParam());
		URL=Config.getLoginUrl();
	}
	
	/*
	 * 提供给手动调用bindDevice使用
	 */
	public BindDevice(String param) {
		super(Util.generateMap(param));
		URL=Config.getLoginUrl();
		// TODO Auto-generated constructor stub
	}

}
