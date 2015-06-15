package com.dangdang.fixtures.login;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.LoginReponse;


/**
 *
 * @author guohaiying
 */
public class Login extends FixtureBase{

	ReponseV2<LoginReponse> reponseResult;
	Long custId=null;

	public Login(){
		URL=Config.getLoginUrl();
	}
	
	public boolean bindAndLogin(String action) throws Exception{
		boolean flag = false;
		Bind bind = new Bind();
		bind.setParameters(paramMap);
		if(bind.doGet("bind")){
		 flag = super.doGet("login", 0);
		}		
		return flag;
	}
	
	public void jsonToClass(){	
		reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<LoginReponse>>(){});
		System.out.println("aaaa"+ reponseResult);
	}
	
	public String getResult(){
		return URL;
	}
		
	//获取结果
	public ReponseV2<LoginReponse> getReponseResult() {
		return reponseResult;
	}
	
	//获取token
	public String getToken(){
		if(reponseResult.getStatus().getCode()==0){
			return reponseResult.getData().getToken();
		}
    	return "";
	}

	public String getCustId() throws Exception{
		return 	getCustId(paramMap.get("userName"));
	}
	
	/**
	 * ��ȡ�û���custID
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public static String getCustId(String userName) throws Exception{
			Map<String, Object> result= DbUtil.selectOne(Config.ECMSDBConfig, "SELECT CUST_ID from user_device where USERNAME='"+userName+"' limit 1");
			return result.get("CUST_ID").toString();		
	}
	
	String n="0";
	public String get(String s) throws Exception{
		bindAndLogin("login");
		if (s.contains("custID")){
			return getCustId();
		}else
			return getToken();
	}
	

	
	
	public static void main(String[] args) throws Exception{
		Config.setLoginUrl("http://10.255.223.131/mobile/api2.do");
		Login login = new Login();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", "z1@123.com");
		map.put("passWord", "111111");
		map.put("loginType", "email");
		map.put("deviceType", "Android");
		map.put("deviceSerialNo", "863151026834264");		
		login.setParameters(map);
		//System.out.println(login.doGet("login"));
		System.out.println(login.getToken());
		System.out.println(login.getCustId());
	}
}
