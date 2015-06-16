package com.dangdang.autotest.common;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.core.InterfaceBase;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;

/**
 * 
 * @author guohaiying
 *
 */
public class FixtureBase extends InterfaceBase{
	Logger log = Logger.getLogger(FixtureBase.class);
	boolean verifyResult;
	Login login;
	
	public FixtureBase(){
		URL = Config.getUrl();
	}
	
	/**
	 * 接收FitNesse上map形式的参数 
	 * @param params
	 * @throws Exception 
	 */
	public void setParameters(Map<String, String> params) throws Exception{
		ParseResult parseResult=ParseParamUtil.parseParam(params);
		paramMap =  params;
		paramMap.putAll(Config.getCommonParam());
		login = parseResult.getLogin();
		handleParam();
	}
	

	
	/**
	 * @param actionName
	 * @throws Exception
	 */
	public boolean doGet(String actionName) throws Exception{
		return doGet(actionName, 0);
	}
	
	/**
	 * @param actionName
	 * @param exceptedCode 鎺ュ彛杩斿洖鐨刢ode鍊�
	 * @throws Exception
	 */
	public boolean doGet(String actionName, int exceptedCode) throws Exception {
		boolean flag = false;
		paramMap.put("action", actionName);
		//paramMap.putAll(Config.getCommonParam());
		result=HttpDriver.doGet(URL, paramMap, bHttps);
		
		if(result.toString().contains("\"code\":"+exceptedCode)){
			flag = true;
			log.info(result.toString());
		}
		if(flag&&exceptedCode==0){
			jsonToClass();
		}
		return flag;
	}
	
	public void doGet() throws Exception{
		paramMap.put("action", "column");
		HttpDriver.doGet(URL, paramMap, bHttps);
	}
	
	public String doRequest() throws Exception {
		result = HttpDriver.doGet(Config.getUrl(), paramMap,false);
		return result.toString();
	}
	
	public void dataVerify(String expectedCode) throws Exception {
		super.dataVerify();
		verifyResult(expectedCode);
	}
	
	public void verifyResult(String expectedCode){
		try{
			Assert.assertEquals(getDataVerifyResult(), EXCEPTSUCCESS);			
			Assert.assertTrue(result.toString().contains("\"code\":"+expectedCode));
			verifyResult = true;
		}
		catch(AssertionError e){
			verifyResult = false;
		}
	}
	
	public boolean getVerifyResult(){
		return verifyResult;
	}
	
	
	/**
	 * 
	 */
	public void jsonToClass(){
		
	}


	/*============================与fitnesse DynamicDecisionTable集成相关函数=================================*/
	/**
	 * 接收FitNesse上ddt表的参数形式
	 * @param name
	 * @param value
	 */
	public void set(String name, String value){
		paramMap.put(name, value);
	}

	public String get(String columnName){
		if(columnName.equals("result")){
			if(getDataVerifyResult()){
				return "成功";
			}
			else {
				return "失败";
			}
		}
		return paramMap.get(columnName);
	}

	/*
    执行DynamicDecisionTable的每一行
     */
	public void execute() throws Exception {

		doWorkAndVerify();

	}

    /*============================fitnesse DynamicDecisionTable设置列值函数=================================*/
	
}
