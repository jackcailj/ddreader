package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.CheckSigninStateReponse;

/**
 * Created by cailianjie on 2015-8-24.
 */
public class CheckSigninState extends FixtureBase{

    ReponseV2<CheckSigninStateReponse> reponseResult;

    public CheckSigninState(){}

    public ReponseV2<CheckSigninStateReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<CheckSigninStateReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getIsSign().toString().toLowerCase(),paramMap.get("flag").toLowerCase()));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(null,reponseResult.getData().getIsSign()), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
