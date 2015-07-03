package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SendEmailVcodeReponse;

import java.lang.reflect.Type;

/**
 * Created by cailianjie on 2015-7-1.
 */
public class SendEmailVcode extends FixtureBase{


    public SendEmailVcode(){addAction("sendEmailVcode");}

}
