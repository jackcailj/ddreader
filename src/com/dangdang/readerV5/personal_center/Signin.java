package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountIntegralItems;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.SignDb;
import com.dangdang.readerV5.reponse.SigninReponse;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class Signin extends FixtureBase{

    ReponseV2<SigninReponse> reponseResult;

    GetAccountInfo masterAccountInfo;
    GetAccountInfo attachAccountInfo;

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


            //验证铃铛获取正确
            masterAccountInfo = new GetAccountInfo(login,true);
            masterAccountInfo.doWorkAndVerify();

            attachAccountInfo = new GetAccountInfo(login,false);
            attachAccountInfo.doWorkAndVerify();

            attachAccountInfo.getReponseAttachResult().getData().setAccountTotal(attachAccountInfo.getReponseAttachResult().getData().getAccountTotal()+prize);

        }


    }

    @Override
    protected void dataVerify() throws Exception {

        GetAccountInfo afterMasterAccountInfo=null;
        GetAccountInfo afterAttachAccountInfo=null;
        if(login!=null) {
            afterMasterAccountInfo = new GetAccountInfo(login, true);
            afterMasterAccountInfo.doWorkAndVerify();

            afterAttachAccountInfo = new GetAccountInfo(login,false);
            afterAttachAccountInfo.doWorkAndVerify();

        }


        if(reponseV2Base.getStatus().getCode()==0){
            //验证返回的数据正确

            dataVerifyManager.add(new ValueVerify<Object>(afterMasterAccountInfo.getReponseMasterResult().getData(),masterAccountInfo.getReponseMasterResult().getData(),true)
                    .setVerifyContent("验证主账户金额是否正确"));


            dataVerifyManager.add(new ValueVerify<Long>(afterAttachAccountInfo.getReponseAttachResult().getData().getAccountTotal(),attachAccountInfo.getReponseAttachResult().getData().getAccountTotal())
                    .setVerifyContent("验证副账户金额是否正确"));

        }
        else{
            //dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getPrizeValue(),null), VerifyResult.SUCCESS);

            if(login!=null) {
                dataVerifyManager.add(new ValueVerify<Object>(afterMasterAccountInfo.getReponseMasterResult().getData(), masterAccountInfo.getReponseMasterResult().getData(),true
                )
                        .setVerifyContent("验证主账户金额是否正确"), VerifyResult.SUCCESS);

                dataVerifyManager.add(new ValueVerify<Long>(afterAttachAccountInfo.getReponseAttachResult().getData().getAccountTotal(), attachAccountInfo.getReponseAttachResult().getData().getAccountTotal()-prize)
                        .setVerifyContent("验证副账户金额是否正确"), VerifyResult.SUCCESS);
            }
        }
        super.dataVerify();
    }
}
