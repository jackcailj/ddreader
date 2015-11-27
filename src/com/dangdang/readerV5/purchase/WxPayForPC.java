package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.WxPayForPCReponse;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by cailianjie on 2015-11-23.
 */
public class WxPayForPC extends FixtureBase{

    ReponseV2<WxPayForPCReponse> reponseResult;

    public WxPayForPC(){}


    public ReponseV2<WxPayForPCReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<WxPayForPCReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getRedirectUrl(),null), VerifyResult.FAILED);
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getRedirectUrl(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
