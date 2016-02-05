package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-6-24.
 */
public class LoginOutV2 extends FixtureBase {

    public LoginOutV2(){addAction("loginOutV2");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            //退出成功后，需要将缓存中登录信息清除
            LoginManager.remove(login);
        }
        super.dataVerify();
    }
}
