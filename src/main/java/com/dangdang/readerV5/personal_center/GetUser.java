package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.db.account.AccountInfo;
import com.dangdang.db.account.AccountUtils;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.ChannelOwner;
import com.dangdang.enumeration.ChannelOwnerStatus;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.readerV5.bookbar.BarMember;
import com.dangdang.readerV5.reponse.GetUserReponse;
import com.dangdang.readerV5.reponse.UserInfo;
import com.dangdang.db.ucenter.UserInfoSql;
import com.dangdang.ucenter.meta.LoginRecord;

import java.util.List;

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
           /* if(login ==null || paramMap.get("pubId")!=null){
                custId=UserInfoSql.getCustIdByPubId(paramMap.get("pubId"));
            }
            else{
                custId=login.getCustId();
            }*/

            if(paramMap.get("selfType").equals("1"))
            {
                ILogin _login= LoginManager.getLogin(paramMap.get("pubUserName"));
                custId = _login.getCustId();
            }
            else
            {
                custId=login.getCustId();
            }


            //获取基本信息
            LoginRecord loginRecord=UserInfoSql.getUserInfoByCustId(custId);
            UserInfo userInfo = new UserInfo();
            //userInfo.setCustId(loginRecord.getCustId());
            userInfo.setCustImg(loginRecord.getCustImg());
            userInfo.setGender(loginRecord.getGender());
            //userInfo.setNickName(loginRecord.getCustNickname());
            //获取账户信息
            if(paramMap.get("selfType").equals("0")){
                AccountInfo accountInfo = AccountUtils.getAccountInfo(custId);
                userInfo.setGoldNum(accountInfo.getMasterAccountMoney());
                userInfo.setGoldNumIos(accountInfo.getMasterAccountMoneyIos());
                userInfo.setSilverNum(accountInfo.getAttachAccountMoney());
                userInfo.setSilverNumIos(accountInfo.getAttachAccountMoneyIos());
                userInfo.setLevel(accountInfo.getAccountGrade());
                userInfo.setDisplayId(loginRecord.getDisplayId());
            }
            else{
                AccountInfo accountInfo = AccountUtils.getAccountInfo(custId);
                userInfo.setGoldNum(0);
                userInfo.setGoldNumIos(0);
                userInfo.setSilverNum(0);
                userInfo.setSilverNumIos(0);
                userInfo.setLevel(accountInfo.getAccountGrade());
            }

            //验证是否有bar或者channel
          /*  List<BarMember> bars = BarMemberDb.getOwnerBars(custId);
            if(bars.size()>0){
                userInfo.setBarOwnerLevel(1);
            }
            else{
                userInfo.setBarOwnerLevel(0);
            }*/

            //验证是否有channel
            try {
                ChannelOwner owner = ChannelDb.getChannelOwner(custId);
                if(owner.getStatus() == ChannelOwnerStatus.AUDITED){
                    userInfo.setChannelOwner(1);
                }
            }catch (Exception e){
                userInfo.setChannelOwner(null);
            }

            dataVerifyManager.add(new ValueVerify<UserInfo>(reponseResult.getData().getUserInfo(), userInfo, true).setVerifyContent("对比用户信息是否一致"));
        }

        super.dataVerify();
    }
}
