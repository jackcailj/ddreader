package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountIntegralItems;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.SignDb;
import com.dangdang.readerV5.reponse.SigninReponse;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class Signin extends FixtureBase{

    ReponseV2<SigninReponse> reponseResult;

    int prize;

    public  Signin(){}

    public ReponseV2<SigninReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SigninReponse>>(){});
    }

    @Override
    protected void genrateVerifyData() throws Exception {

        if(login!=null){
            prize=SignDb.getSignPrize(Integer.parseInt(paramMap.get("flag")));
            //验证获取的积分正确
            AccountIntegralItems accountIntegralItems = new AccountIntegralItems();
            accountIntegralItems.setCustId(Long.parseLong(login.getCustId()));
            accountIntegralItems.setActionType(19);
            accountIntegralItems.setIntegral(prize);

            dataVerifyManager.add(new RecordExVerify(Config.ACCOUNTDBConfig, accountIntegralItems, "integral_items_id", " from account_integral_items where cust_id=" + login.getCustId())
                    ,isEXCEPTSUCCESS()?VerifyResult.SUCCESS:VerifyResult.FAILED);

        }


    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            //验证返回的数据正确

            //接口更改，不返回奖励金额，改为返回提示信息
            //dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getPrizeValue(),prize));
        }
        else{
            //dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPrizeValue(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
