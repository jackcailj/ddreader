package com.dangdang.common.functional.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframework.core.ConfigCore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cailianjie on 2015-6-16.
 */
public class LoginManager {
    //Login对象管理，记录每个用户的Login对象
    static Map<String, ILogin> loginManager = new HashMap<String, ILogin>();
    static Map<String, ILogin> loginManager_custID = new HashMap<String, ILogin>();
    static Map<String, ILogin> loginManager_pubID = new HashMap<String, ILogin>();

    public static ILogin getLogin(LoginInfo loginInfo) throws Exception {
        ILogin loginObject=null;
        if(!loginManager.containsKey(loginInfo.getUserName())){
            if(ConfigCore.getTestData().equals("readerV4")) {
                BindDevice bind = new BindDevice((Map<String, String>) JSONObject.toJSON(loginInfo));
                bind.doWork();

                Login login = new Login(loginInfo);
                login.doWorkAndVerify();
                if (login.getReponseResult().getStatus().getCode() == 0) {
                    loginManager.put(loginInfo.getUserName(), login);
                    loginObject=login;
                }
            }
            else
            {
                ddLogin dlogin = new ddLogin(loginInfo);
                dlogin.doWorkAndVerify();
                if (dlogin.getReponseStatus().getStatus().getCode() == 0) {
                    loginManager.put(loginInfo.getUserName(), dlogin);
                    loginManager_custID.put(dlogin.getCustId(),dlogin);
                    loginManager_pubID.put(dlogin.getPubId(),dlogin);
                    loginObject=dlogin;
                }
            }
        }
        else{
            loginObject = loginManager.get(loginInfo.getUserName());
        }


        return loginObject;
    }

    /*
    通过用户名称获取Login实例
     */
    public static ILogin getLogin(String userName){
        return  loginManager.get(userName);
    }


    /*
    通过custid获取login
     */
    public static ILogin getLoginByCustID(String custID){
        return  loginManager_custID.get(custID);
    }

    /*
    通过pubid获取login
     */
    public static ILogin getLoginByPubID(String pubId){
        return  loginManager_pubID.get(pubId);
    }

    public static ILogin getLogin(Map<String,String> param) throws Exception {
        LoginInfo loginInfo = JSONObject.parseObject(JSONObject.toJSONString(param),LoginInfo.class);
        return getLogin(loginInfo);
    }
}
