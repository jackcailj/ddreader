package com.dangdang.common.functional.login;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.LoginReponse;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-15.
 */
public class ddLogin extends FixtureBase implements ILogin{

    LoginInfo loginInfo;

    ReponseV2<LoginReponse> reponseResult;

    public   ddLogin(){
    }

    public  ddLogin(LoginInfo Info){
        URL= Config.getLoginUrl();
        loginInfo = Info;
        paramMap.putAll((Map<String, String>) JSONObject.toJSON(Info));
        addAction("ddLogin");
    }
    
    //Add by guohaiying 解决问题：fitnesse上单独调用ddLogin接口时使用
	public void parseParameters(Map<String, String> params) throws Exception{
		paramMap =  params;
		paramMap.putAll(Config.getCommonParam());
	}



    @Override
    public String getCustId() throws Exception{
        return getReponseResult().getData().getUser().getId().toString();
    }

    @Override
    public String getPubId() {
        return reponseResult.getData().getUserPubId();
    }


    @Override
    public String getToken() {
        return getReponseResult().getData().getToken();
    }
   /* public String get(String param) {
    	return getReponseResult().getData().getToken();
    }*/

    @Override
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    @Override
    public UserInfo getUserInfo() {
        return reponseResult.getData().getUser();
    }

    public ReponseV2<LoginReponse> getReponseResult() {
        if(reponseResult ==null) {

            reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<LoginReponse>>(){});
        }
        return reponseResult;
    }
}
