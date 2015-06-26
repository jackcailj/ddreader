package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.AccountType;
import com.dangdang.account.AccountUtils;
import com.dangdang.account.meta.AccountConsumeItems;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetAccountInfoReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-19.
 */
public class GetAccountInfo extends FixtureBase{

    ReponseV2<GetAccountInfoReponse> reponseResult;

    public GetAccountInfo(){addAction("getAccountInfo");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        //ParseParamUtil.removeBlackParam(paramMap);
    }

    public ReponseV2<GetAccountInfoReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetAccountInfoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List accountItems=null;
            if(paramMap.get("accountType").equals("master")){
                //获取金铃铛信息
                accountItems= AccountUtils.getMasterLingDangDetail(login.getCustId(),Config.getDevice());
            }
            else if(paramMap.get("accountType").equals("attach"))
            {
                //获取银铃铛信息
                accountItems= AccountUtils.getAttachLingDangDetail(login.getCustId(), Config.getDevice());
            }

            if(reponseResult.getData().getResult()!=null) {
                dataVerifyManager.add(new ListVerify(accountItems, reponseResult.getData().getResult(), true).setVerifyContent("验证账户金额和记录是否正确"));
            }
        }
        super.dataVerify();
    }
}
