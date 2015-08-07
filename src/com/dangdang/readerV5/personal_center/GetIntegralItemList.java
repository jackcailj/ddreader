package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.AccountUtils;
import com.dangdang.account.IntegralItem;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetIntegralItemListReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-5.
 */
public class GetIntegralItemList extends FixtureBase{

    ReponseV2<GetIntegralItemListReponse> reponseResult;

    public GetIntegralItemList(){}


    public ReponseV2<GetIntegralItemListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetIntegralItemListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<IntegralItem> integralItems = AccountUtils.getIntegralItems(login.getCustId());

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getAccountIntegralList(),integralItems,true));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getAccountIntegralList(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
