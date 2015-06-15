package com.dangdang.reader.functional.param.parse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dangdang.common.functional.login.BindDevice;
import com.dangdang.common.functional.login.BindInfo;
import com.dangdang.common.functional.login.Login;



/*
 * 自动登录参数解析
 * 规则：
 * 1、配置文件中需要有username、pwd、type字段
 * 2、如果这三个字段不为空，则自动登录，生成token值放到参数中，并移除这三个字段
 */
public class LoginParamParse implements IParamParse{

	
	public Login parse(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		/*
		 *1、自动登录
		 *2、如果字段为空，代表不传此字段
		 */
		// TODO Auto-generated method stub
		Login login =null;
		if((param.get("userName")!=null && StringUtils.isNotBlank(param.get("userName").toString())
				&& (param.get("token")==null ||(param.get("token")!=null && StringUtils.isBlank(param.get("token").toString()))))){
			//BindDevice bind = new BindDevice(param);
			//bind.doWork();
			//绑定设备代码放到Login.getLogin中，保证每次只执行一次。
			login=Login.getLogin(param);
			//Login login =new Login(param);
			//login.doWork();
			param.put("token", login.getToken());
		}		
		
		param.remove("userName");
		param.remove("passWord");
		param.remove("loginType");
		
		return login;
	}

}
