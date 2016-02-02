package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountActionTypeInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.AccountActionTypeInfoDb;
import com.dangdang.db.account.AccountExperienceItemsDb;
import com.dangdang.db.account.AccountIntegralItemsDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.DeleteDigestResponse;

public class DeleteDigest extends FixtureBase{	
	ReponseV2<DeleteDigestResponse> jsonResult;
	String _userExperience; //用户经验值
	String _userIntegral; //用户积分
	String custId;
	
	/*
	 * 生成需要验证的数据
	 * //个人发表文章/攻略 30  后台删除文章/攻略31  道长删除文章/攻略32
	 */
	@Override
	protected void genrateVerifyData() throws Exception {
		custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
		_userExperience = AccountExperienceItemsDb.getUserExperience(custId);
		_userIntegral = AccountIntegralItemsDb.getUserIntegral(custId);
	}
	
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DeleteDigestResponse>>(){});
	}

	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){	
			String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
			//个人发表文章/攻略 30  后台删除文章/攻略31  道长删除文章/攻略32
			AccountActionTypeInfo info = AccountActionTypeInfoDb.getAccountActionType(32);			
	        //验证json与数据库中数据
			String userExperience = AccountExperienceItemsDb.getUserExperience(custId);
			String userIntegral = AccountIntegralItemsDb.getUserIntegral(custId);
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getExperience(), String.valueOf(info.getExperienceAward())).setVerifyContent("验证经验值"));
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getIntegral(), String.valueOf(info.getIntegralAward())).setVerifyContent("验证积分值"));
			
			//验证经验/积分总值
			dataVerifyManager.add(new ValueVerify<Integer>(Integer.valueOf(_userExperience), Integer.valueOf(userExperience)-info.getExperienceAward()).setVerifyContent("验证经验总值"));
			dataVerifyManager.add(new ValueVerify<Integer>(Integer.valueOf(_userIntegral), Integer.valueOf(userIntegral)-info.getIntegralAward()).setVerifyContent("验证积分总值"));
		}
		super.dataVerify();
	}
}
