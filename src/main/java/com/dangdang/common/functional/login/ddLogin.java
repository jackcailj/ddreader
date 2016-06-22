package com.dangdang.common.functional.login;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.ucenter.UserInfoSql;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.reponse.LoginReponse;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-15.
 */
public class ddLogin extends FixtureBase implements ILogin{

    LoginInfo loginInfo;
    //String custId;

    ReponseV2<LoginReponse> reponseResult;

    public   ddLogin(){
        addAction("ddLogin");
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

    public void setReponseResult(ReponseV2<LoginReponse> reponseResult) {
        this.reponseResult = reponseResult;
    }

    @Override
    protected void parseParam() throws Exception {
        //为了兼容cailj_ddtest@126.com账户线上线下密码不一致问题。
        //暂时这么处理
        if(Config.getEnvironment()!= TestEnvironment.TESTING
                && paramMap.get("userName").equals("cailj_ddtest@126.com")
                && paramMap.get("passWord").equals("ddtest")){
            paramMap.put("passWord","111111");
        }
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();
        getReponseResult();
    }

    @Override
    public String getCustId() throws Exception{
       /* if(StringUtils.isBlank(custId)){
            custId=UserInfoSql.getCustIdByName(loginInfo.getUserName());
        }
        return custId;*/
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

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
