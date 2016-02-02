package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.common.functional.login.LoginInfo;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.common.functional.login.ddLogin;
import com.dangdang.db.ucenter.UserInfoSql;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-18.
 * 通过名字获取pubId
 *
 * 格式：#GetPubIdByName#用户名，密码
 */
public class GetPubIdByNameParse implements  IParamParse{

    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        if(StringUtils.isNotBlank(param)) {
            String[] params = ParamParse.parseParam(param);

            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserName(params[0].trim());
            loginInfo.setPassWord(params[1].trim());
            ILogin login= LoginManager.getLogin(loginInfo);


            //String pubId= UserInfoSql.getPubIdByName(param);
            paramMap.put(key,login.getPubId());
        }
        else {
            throw new Exception("GetPubIdByName，用户名为空");
        }
    }

}
