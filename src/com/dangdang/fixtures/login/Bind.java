package com.dangdang.fixtures.login;

import java.util.HashMap;
import java.util.Map;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;

public class Bind extends FixtureBase{
    String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJfkzSc27DRThyR"
		     + "AUVN8wqEmqkoUf4g6u%2B1zMRCqU%2FsWS5SXMwwYk4ewuPco6zooLA9D3nzuzCwl2SVtismOV5sCAwEAAQ%3D%3D";
    	
    public Bind(){
    	URL=Config.getLoginUrl();
    }

	
	@Override
	public boolean doGet(String actionName) throws Exception{
		return doGet(actionName, 0);	
	}
	
	@Override
	public boolean doGet(String actionName, int exceptedCode) throws Exception{
		paramMap.put("key", key);
		return super.doGet(actionName, exceptedCode);	
		
	}
	
	public String getResult(){
		return URL + " "+result.toString();
	}
    public String getKey() {
    	return key;
    }
    
    public void setKey(String key) {
    	this.key = key;
    }
	
	public static void main(String[] args) throws Exception{
		Config.setLoginUrl("http://10.255.223.131/mobile/api2.do");
		Bind bind = new Bind();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", "z16@123.com");
		map.put("passWord", "111111");
		map.put("loginType", "email");
		map.put("deviceType", "Android");
		map.put("deviceSerialNo", "863151026834264");		
		bind.setParameters(map);
		System.out.println(bind.doGet("bind"));
	}
}
