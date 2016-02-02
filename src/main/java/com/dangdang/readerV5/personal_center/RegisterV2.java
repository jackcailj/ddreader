package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginInfo;
import com.dangdang.common.functional.login.ddLogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.param.parse.ParseParamUtil;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-19.
 */
public class RegisterV2 extends FixtureBase{

    public RegisterV2(){addAction("registerV2");}

    @Override
    protected void beforeParseParam() throws Exception {
        //增加action字段
        if(paramMap.get("action")==null) {
            addAction(lowerFirst(this.getClass().getSimpleName()));
        }

        //解析参数
        ParseParamUtil.parseOperateParam(paramMap);

        //添加公共参数
        for(Map.Entry<String,String> entry: Config.getCommonParam().entrySet()){
            if(!paramMap.containsKey(entry.getKey())){
                paramMap.put(entry.getKey(),entry.getValue());
            }
        }
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserName(paramMap.get("userName"));
            loginInfo.setPassWord(paramMap.get("passWord"));
            ddLogin login=new ddLogin(loginInfo);
            login.doWorkAndVerify();

            dataVerifyManager.add(new ValueVerify<Integer>(login.getReponseResult().getStatus().getCode(),0));
            dataVerifyManager.add(new ValueVerify<String>(login.getReponseResult().getData().getToken(),null), VerifyResult.FAILED);
        }
        super.dataVerify();
    }
}
