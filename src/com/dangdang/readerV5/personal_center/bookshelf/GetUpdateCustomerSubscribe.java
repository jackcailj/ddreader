package com.dangdang.readerV5.personal_center.bookshelf;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.CustomerSubscribeDb;
import com.dangdang.digital.meta.MediaCustomerSubscription;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class GetUpdateCustomerSubscribe extends FixtureBase{

    Map<String,String> data =new HashMap<String, String>();

    public GetUpdateCustomerSubscribe(){}

    @Override
    protected void genrateVerifyData() throws Exception {
        if(login!=null && EXCEPTSUCCESS==false && StringUtils.isNotBlank(paramMap.get("mediaId"))){
            String[] medias=paramMap.get("mediaId").split("_");

            for(String meidaId:medias) {
                try {
                    MediaCustomerSubscription subscription = CustomerSubscribeDb.getCustomerSubscription(login.getCustId(), meidaId, paramMap.get("appId"));
                    data.put(meidaId, subscription.getStatus().toString());
                }
                catch (Exception e){}
            }
        }
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            if(!paramMap.get("flag").equals("xiajia")) {
                String[] medias = paramMap.get("mediaId").split("_");

                for (String meidaId : medias) {
                    MediaCustomerSubscription subscription = CustomerSubscribeDb.getCustomerSubscription(login.getCustId(), meidaId, paramMap.get("appId"));
                    dataVerifyManager.add(new ValueVerify<String>(subscription.getStatus().toString(), paramMap.get("operationType")));
                }
            }
        }
        else{
            if(login!=null && StringUtils.isNotBlank(paramMap.get("mediaId"))  ){
                String[] medias=paramMap.get("mediaId").split("_");

                for(String meidaId:medias) {
                    try {
                        MediaCustomerSubscription subscription = CustomerSubscribeDb.getCustomerSubscription(login.getCustId(), meidaId, paramMap.get("appId"));
                        dataVerifyManager.add(new ValueVerify<String>(subscription.getStatus().toString(), data.get("meidaId")));
                    }catch (Exception e){

                    }
                }
            }
        }
        super.dataVerify();
    }
}
