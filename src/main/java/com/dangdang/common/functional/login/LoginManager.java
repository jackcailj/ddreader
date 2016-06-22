package com.dangdang.common.functional.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.DesUtils;
import com.dangdang.enumeration.VarKey;
import com.dangdang.reader.functional.reponse.LoginReponse;
import com.dangdang.ucenter.meta.UserDevice;
import org.apache.commons.lang3.StringUtils;

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

    public static void AddByUserName(String userName,ILogin login){
        loginManager.put(userName,login);
    }

    public static void AddByCustId(String custId,ILogin login){
        loginManager_custID.put(custId,login);
    }

    public static void AddByPubId(String pubId,ILogin login){
        loginManager_pubID.put(pubId,login);
    }

    public static ILogin getLogin(LoginInfo loginInfo) throws Exception {

        // TODO Auto-generated method stub
        if(Config.getEnvironment()!= TestEnvironment.TESTING){
            //if (StringUtils.isNotBlank(loginInfo.getUserName())) {

                //如果存在ILogin，则直接返回
                ILogin ilogin =LoginManager.getLogin(loginInfo.getUserName());
                if(ilogin!=null){
                    return ilogin;
                }

                ddLogin login = new ddLogin();
                ReponseV2<LoginReponse> reponse = new ReponseV2<LoginReponse>();
                reponse.setData(new LoginReponse());

                reponse.getData().setUser(new UserInfo());


                login.setReponseResult(reponse);

                login.setLoginInfo(loginInfo);

                UserDevice device = DbUtil.selectOne(Config.UCENTERDBConfig,"SELECT * from user_device where USERNAME ='"+loginInfo.getUserName()+"'  order by LAST_LOGIN_TIME DESC limit 1", UserDevice.class);

                reponse.getData().setToken(device.getLoginToken());
                reponse.getData().getUser().setId(device.getCustId());
                reponse.getData().getUser().setUserName(device.getUsername());
                reponse.getData().setUserPubId(DesUtils.encryptCustId(device.getCustId()));
                VariableStore.add(VarKey.LOGIN, login);
                LoginManager.AddByUserName(login.getUserInfo().getUserName(),login);
                LoginManager.AddByCustId(login.getCustId(),login);
                LoginManager.AddByPubId(login.getPubId(),login);

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

                //param.put("token", login.getToken());


                return login;
            //}
        }


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

    /*
    清除某个登录信息
     */
    public static void remove(ILogin login) throws Exception {
        if(login!=null) {
            loginManager.remove(login.getLoginInfo().getUserName());
            loginManager_custID.remove(login.getCustId());
            loginManager_pubID.remove(login.getPubId());
        }
    }
}
