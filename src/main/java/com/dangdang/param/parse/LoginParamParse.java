package com.dangdang.param.parse;

import java.util.Map;

import com.dangdang.common.functional.login.*;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.enumeration.VarKey;
import com.dangdang.reader.functional.reponse.LoginReponse;
import com.dangdang.ucenter.meta.UserDevice;
import org.apache.commons.lang3.StringUtils;


/*
 * 自动登录参数解析
 * 规则：
 * 1、配置文件中需要有username、pwd、type字段
 * 2、如果这三个字段不为空，则自动登录，生成token值放到参数中，并移除这三个字段
 */
public class LoginParamParse implements IParamParse{

	
	public ILogin parse(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		/*
		 *1、自动登录
		 *2、如果字段为空，代表不传此字段
		 */


		ILogin login =null;
		if((param.get("userName")!=null && StringUtils.isNotBlank(param.get("userName").toString())
				&&param.get("passWord")!=null
				&& (param.get("token")==null ||(param.get("token")!=null && StringUtils.isBlank(param.get("token").toString()))))){
			//BindDevice bind = new BindDevice(param);
			//bind.doWork();
			//绑定设备代码放到Login.getLogin中，保证每次只执行一次。
			login= LoginManager.getLogin(param);
			//Login login =new Login(param);
			//login.doWork();
			param.put("token", login.getToken());

			VariableStore.add(VarKey.LOGIN.toString(),login);

			param.remove("userName");
			param.remove("passWord");
			param.remove("loginType");
		}		
		

		
		return login;
	}

	@Override
	public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

	}
}
