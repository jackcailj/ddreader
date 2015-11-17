package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.ObjectArraySerializer;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.TopsReponse;

/**
 * Created by cailianjie on 2015-11-10.
 */
public class Tops extends FixtureBase{

    ReponseV2<TopsReponse> reponseResult;

    public Tops(){}

    public ReponseV2<TopsReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<TopsReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getSaleList(),null), VerifyResult.FAILED);
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getTotal(),null), VerifyResult.FAILED);
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getSaleList(),null), VerifyResult.SUCCESS);
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getTotal(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
