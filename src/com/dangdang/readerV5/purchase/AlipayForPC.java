package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.AlipayForPCReponse;
import com.dangdang.readerV5.reponse.WxPayForPCReponse;

/**
 * Created by cailianjie on 2015-11-23.
 */
public class AlipayForPC extends FixtureBase{
    ReponseV2<AlipayForPCReponse> reponseResult;

    public AlipayForPC(){}


    public ReponseV2<AlipayForPCReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AlipayForPCReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getEmbedCode(),null), VerifyResult.FAILED);
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getEmbedCode(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
