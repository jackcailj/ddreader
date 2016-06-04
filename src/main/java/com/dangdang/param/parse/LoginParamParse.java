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
		// TODO Auto-generated method stub
        if(Config.getEnvironment()!=TestEnvironment.TESTING){
            if (StringUtils.isNotBlank(param.get("userName"))) {
                ddLogin login = new ddLogin();
                ReponseV2<LoginReponse> reponse = new ReponseV2<LoginReponse>();
                reponse.setData(new LoginReponse());

                reponse.getData().setUser(new UserInfo());


                login.setReponseResult(reponse);

                UserDevice device = DbUtil.selectOne(Config.UCENTERDBConfig,"SELECT * from user_device where USERNAME ='cailj_ddtest@126.com' order by LAST_LOGIN_TIME DESC limit 1", UserDevice.class);

                reponse.getData().setToken(device.getLoginToken());
                reponse.getData().getUser().setId(device.getCustId());
                VariableStore.add(VarKey.LOGIN, login);

                /*if (param.get("userName").equals("cailj_ddtest@126.com")) {
                    reponse.getData().setToken("b7592f89eab9cbccf119910ca49d965c");
                    reponse.getData().getUser().setId(184355013l);
                    VariableStore.add(VarKey.LOGIN, login);
                } else if (param.get("userName").equals("whytest@dd.con")) {
                    reponse.getData().setToken("d3a5a14272bc614749a16158333707a6");
                    reponse.getData().getUser().setId(170077404l);
                    VariableStore.add(VarKey.LOGIN, login);
                } else if (param.get("userName").equals("z11@123.com")) {
                    reponse.getData().setToken("5d143e8371165a1819b2a55ff06fa7dc");
                    reponse.getData().getUser().setId(124081034l);
                    VariableStore.add(VarKey.LOGIN, login);
                }*/

                param.put("token", login.getToken());

                return login;
            }
        }

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
