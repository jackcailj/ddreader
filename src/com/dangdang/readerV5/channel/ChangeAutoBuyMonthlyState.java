package com.dangdang.readerV5.channel;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;

/**
 * 
 * @author guohaiying
 *
 */
public class ChangeAutoBuyMonthlyState extends FixtureBase{
	String monthlyState1 ="";
	String custId = "";
	String channelId = "";
	
    @Override
	protected void genrateVerifyData() throws Exception {
    	custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
    	channelId = paramMap.get("cId");
    	monthlyState1 = MediaMonthlyAuthorityDb.getUserMonthlyState(custId, channelId);
	}

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String renew = paramMap.get("isAutomaticallyRenew");
    		String state = paramMap.get("isAutomaticallyRenew");
    		String monthlyState2 = MediaMonthlyAuthorityDb.getUserMonthlyState(custId, channelId);
        	if(renew.equals("1")||renew.equals("0")){
        		dataVerifyManager.add(new ValueVerify<String>(state, monthlyState2).setVerifyContent("验证参数与数据库自动续费状态是否一致"));
        		dataVerifyManager.add(new ExpressionVerify(
        				(Integer.valueOf(monthlyState1) + Integer.valueOf(monthlyState2))==1).setVerifyContent("验证更新前后的自动续费状态"));
        	}else{
        		dataVerifyManager.add(new ValueVerify<String>("0", monthlyState2).setVerifyContent("验证isAutomaticallyRenew参数非法时自动续费状态"));
        	}
        }
        super.dataVerify();
    }
}
