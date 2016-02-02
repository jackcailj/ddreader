package com.dangdang.autotest.common;

import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.core.FunctionalBase;
import com.dangdang.ddframework.core.InterfaceBase;

/*
 * 增加公共参数
 */
public class FunctionalBaseEx extends InterfaceBase{
	
	//登录相关数据
	protected  ILogin login;
	
	public FunctionalBaseEx(){
		URL=Config.getUrl();
	}
	
	
	public FunctionalBaseEx(Map<String,String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		URL=Config.getUrl();
	}
	
	public FunctionalBaseEx(String param) {
		// TODO Auto-generated constructor stub
		super(param);
		//paramMap.putAll(Config.getCommonParam());
		URL=Config.getUrl();
	}
	
	@Override
	public void beforeParseParam() throws Exception {
		// TODO Auto-generated method stub
		addCommonParam();
		super.beforeParseParam();
	}
	
	@Override
	public void beforeRequest() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
	}
	
	/*
	 * 添加action参数
	 */
	public void addAction(String action){
		paramMap.put("action", action);
	}
	
	/*
	 * 增加公共参数
	 */
	public void addCommonParam(){
		paramMap.putAll(Config.getCommonParam());
	}
	
	
	/*
	 * 设置login，增加逻辑，只有不为null时才赋值
	 */
	public void setLogin(ILogin loginArg){
		if(loginArg!=null){
			login=loginArg;
		}
	}
}
