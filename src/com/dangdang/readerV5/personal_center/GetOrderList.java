package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetOrderListReponse;

/**
 * Created by cailianjie on 2015-7-9.
 */
public class GetOrderList extends FixtureBase{

    ReponseV2<GetOrderListReponse> reponseResult;

    public GetOrderList(){addAction("getOrderList");}

    public ReponseV2<GetOrderListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetOrderListReponse>>(){});
    }

    public String getOrderId(){
        return reponseResult.getData().getResult().getOrders().get(0).getOrderId();
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getResult().getErrorCode(),0));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getResult(),null), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}