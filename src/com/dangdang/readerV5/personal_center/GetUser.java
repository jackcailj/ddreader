package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.AccountInfo;
import com.dangdang.account.AccountUtils;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetUserReponse;
import com.dangdang.readerV5.reponse.UserInfo;
import com.dangdang.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.LoginRecord;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetUser extends FixtureBase{



    ReponseV2<GetUserReponse> reponseResult;

    public GetUser(){
        addAction("getUser");
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
    }

    public ReponseV2<GetUserReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetUserReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            String custId="";
            if(login ==null || paramMap.get("pubId")!=null){
                custId=UserInfoSql.getCustIdByPubId(paramMap.get("pubId"));
            }
            else{
                custId=login.getCustId();
            }

            //获取基本信息
            LoginRecord loginRecord=UserInfoSql.getUserInfoByCustId(custId);
            UserInfo userInfo = new UserInfo();
            userInfo.setCustId(loginRecord.getCustId());
            userInfo.setCustImg(loginRecord.getCustImg());
            userInfo.setDisplayId(loginRecord.getDisplayId());
            userInfo.setGender(loginRecord.getGender());
            userInfo.setNickName(loginRecord.getCustNickname());
            //获取账户信息
            AccountInfo accountInfo = AccountUtils.getAccountInfo(custId);
            userInfo.setGoldNum(accountInfo.getMasterAccountMoney());
            userInfo.setGoldNumIos(accountInfo.getMasterAccountMoneyIos());
            userInfo.setSilverNum(accountInfo.getAttachAccountMoney());
            userInfo.setSilverNumIos(accountInfo.getAttachAccountMoneyIos());
            userInfo.setLevel(accountInfo.getAccountGrade());

            dataVerifyManager.add(new ValueVerify<UserInfo>(userInfo,reponseResult.getData().getUserInfo(),true).setVerifyContent("对比用户信息是否一致"));
        }

        super.dataVerify();
    }
}
