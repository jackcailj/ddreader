package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.common.functional.login.ddLogin;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.param.parse.ParseParamUtil;


/**
 * Created by cailianjie on 2015-6-19.
 */
public class ChangeIntroduct extends FixtureBase{

    public ChangeIntroduct(){addAction("changeIntroduct");}

    @Override
    protected void parseParam() throws Exception {
        //setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
/*            ddLogin dlogin = new ddLogin(login.getLoginInfo());
            LoginManager.remove(login);

            dlogin.doWork();

            dataVerifyManager.add(new ValueVerify<String>(dlogin.getUserInfo().getIntroduct(),paramMap.get("content")));*/
        }
        super.dataVerify();
    }
}
