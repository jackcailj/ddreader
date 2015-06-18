package com.dangdang.common.functional.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.FunctionalBase;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.reader.functional.reponse.LoginReponse;

public class Login  extends FunctionalBaseEx implements ILogin{

	LoginInfo	loginInfo;
	ReponseV2<LoginReponse> reponseResult;
	Long custId=null;
	
	//Login对象管理，记录每个用户的Login对象
	static Map<String, Login> loginManager = new HashMap<String, Login>();

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	/*
	 * 可以不包括action字段
	 */
	public Login(Map<String, String> param) {
		loginInfo = JSONObject.parseObject(JSONObject.toJSONString(param),LoginInfo.class);
		init(loginInfo);
		
	}


	
	public Login(LoginInfo loginInfoArg){
		loginInfo =loginInfoArg;
		init(loginInfo);
	} 
	
	/*
	 * 获取结果
	 */
	public ReponseV2<LoginReponse> getReponseResult() {
		return reponseResult;
	}
	
	/*
	 * 根据LoginInfo生成参数
	 */
	protected void init(LoginInfo loginInfo){
		paramMap=(Map<String, String>) JSONObject.toJSON(loginInfo);
		originalParamMap.putAll(paramMap);
		paramMap.putAll(Config.getCommonParam());
		URL=Config.getLoginUrl();
	}
	
	/*
	 * 提供给手动调用login使用
	 */
	public Login(String param) {
		super(Util.generateMap(param));
		URL=Config.getLoginUrl();
		// TODO Auto-generated constructor stub
	}
	
	/*
	 *获取token
	 */

	public String getToken(){
		if(reponseResult.getStatus().getCode()==0){
			return reponseResult.getData().getToken();
		}
    	return "";
	}

	/**
	 * 获取用户的custID
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public static String getCusId(String userName) throws Exception{
			Map<String, Object> result= DbUtil.selectOne(Config.ECMSDBConfig, "SELECT CUST_ID from user_device where USERNAME='"+userName+"' limit 1");
			return result.get("CUST_ID").toString();
		
	}
	
	/*
	 * 已登录用户获取custid只获取一次
	 */
	public String getCustId() throws Exception {
		if(custId==null){
			custId =Long.parseLong(getCusId(loginInfo.getUserName()));
		}
		
		return custId.toString();
	}
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<LoginReponse>>(){});
	}
	//--------------------------------------------------------loginManager相关方法------------------------------------------------
	/*
	 * 获取Login对象，实现仅一次登录。
	 * 参数：
	 * 		loginInfo：登录信息
	 */
	public static Login getLogin(LoginInfo loginInfo) throws Exception {
		if(!loginManager.containsKey(loginInfo.getUserName())){
			BindDevice bind = new BindDevice((Map<String, String>)JSONObject.toJSON(loginInfo));
			bind.doWork();
			
			Login login = new Login(loginInfo);
			login.doWorkAndVerify();
			if(login.getReponseResult().getStatus().getCode()==0){
				loginManager.put(loginInfo.getUserName(), login);
			}
		}
		return loginManager.get(loginInfo.getUserName());
	}
	
	/*
	 * 获取Login对象，实现仅一次登录。
	 * 参数：
	 * 		param：登录信息，可以转换成LoginInfo
	 */
	public static Login getLogin(Map<String,String> param) throws Exception {
		LoginInfo loginInfo = JSONObject.parseObject(JSONObject.toJSONString(param),LoginInfo.class);
		return getLogin(loginInfo);
	}
}
