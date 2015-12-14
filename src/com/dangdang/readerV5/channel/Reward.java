package com.dangdang.readerV5.channel;

import com.dangdang.account.meta.MasterAccount;
import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.MasterAccountDB;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;

public class Reward extends FixtureBase{
	
	String custId;
	String channelId;
	String strategyId;
	MediaMonthlyAuthority dbResult1;
	MasterAccount masterAccount1;

    @Override
	protected void genrateVerifyData() throws Exception {
    	custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
    	channelId = paramMap.get("cId");
    	dbResult1 = MediaMonthlyAuthorityDb.get(custId, channelId);   	
    	masterAccount1 = MasterAccountDB.getUserMasterAccount(custId);
    	if(masterAccount1.getMasterAccountMoney()<500){//如果账户余额不足，则先设置主账户余额
    		MasterAccountDB.SetUserAccount(custId, "master_account_money");
    		masterAccount1 = MasterAccountDB.getUserMasterAccount(custId);
    	}
	}
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	//List<UrlList>  actual = ChannelBgImgDb.getBackImg(paramMap.get("type"));
            //dataVerifyManager.add(new ListVerify(jsonResult.getData().getUrlList(),actual, true));           
        
        	//4.验证扣费是否正确
        	String device = paramMap.get("deviceType");
        	int cons = Integer.valueOf(paramMap.get("cons"));
        	MasterAccount masterAccount2 = MasterAccountDB.getUserMasterAccount(custId);
        	if(device.equals("Android")||device.equals("android")){
        		dataVerifyManager.add(new ExpressionVerify((masterAccount1.getMasterAccountMoney()-masterAccount2.getMasterAccountMoney())==cons?true:false).setVerifyContent("验证android打赏后账户金额"));
        		dataVerifyManager.add(new ExpressionVerify(masterAccount1.getMasterAccountMoneyIos()==masterAccount2.getMasterAccountMoneyIos()?true:false).setVerifyContent("验证ios打赏后账户金额"));
        	}else{
        		dataVerifyManager.add(new ExpressionVerify((masterAccount1.getMasterAccountMoneyIos()-masterAccount2.getMasterAccountMoneyIos())==cons?true:false).setVerifyContent("验证ios打赏后账户金额"));
        		dataVerifyManager.add(new ExpressionVerify(masterAccount1.getMasterAccountMoney()==masterAccount2.getMasterAccountMoney()?true:false).setVerifyContent("验证android打赏后账户金额"));
        	}
        	
        	//验证插入表的数据 media_ebook_cons_record
        	
        	
        	//验证插入表的数据 media_consumer_consume
        	
        	
        	//验证插入表的数据 media_ebook_cons_record
        }
        super.dataVerify();
    }
	
}
