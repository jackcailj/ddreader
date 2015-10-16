package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetOrderListReponse;

/**
 * 获取纸书订单详情
 * Created by cailianjie on 2015-7-9.
 */
public class GetOrderDetail extends FixtureBase{

    ReponseV2<GetOrderListReponse> reponseResult;

    public GetOrderDetail(){addAction("getOrderDetail");}

    public ReponseV2<GetOrderListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetOrderListReponse>>(){});
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
