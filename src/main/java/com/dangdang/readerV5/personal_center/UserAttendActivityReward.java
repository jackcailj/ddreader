package com.dangdang.readerV5.personal_center;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.account.meta.MasterAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.AccountInfoData;

public class UserAttendActivityReward extends FixtureBase{

	public ReponseV2<AccountInfoData> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AccountInfoData>>(){});
	}
	
	String deviceType;
	String itemType;
	String id;
	String code;
	boolean hasReceive = false;
	int attachM;
	int attachMIOS;
	int masterM;
	int masterMIOS;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
	    deviceType = params.get("deviceType").toString();
	    super.setParameters(params);
	    paramMap.put("deviceType", deviceType);	    
	    if(deviceType.equalsIgnoreCase("Android")){
	    	itemType = "Android";
	    }
	    else{
	    	itemType = "IOS";
	    }
	    if(paramMap.get("attachAccountActivityId").equalsIgnoreCase("FromDB")){
	    	String sql = "SELECT * FROM attach_account_activity where channel_id=39000 "
		    		   + "and activity_status=1 and item_type='"+itemType+"' limit 1";
		    Map<String,Object> map = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql);
		    id = map.get("attach_account_activity_id").toString();
		    code = map.get("activity_code").toString();
		    paramMap.put("attachAccountActivityId", map.get("attach_account_activity_id").toString());
		    
		    sql = "select * from attach_account where cust_id="+login.getCustId();
			AttachAccount attach =DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
			attachM = attach.getAttachAccountMoney();
			attachMIOS = attach.getAttachAccountMoneyIos();
			
			sql = "select * from master_account where cust_id="+login.getCustId();
			MasterAccount master =DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, MasterAccount.class);
			masterM = master.getMasterAccountMoney();
			masterMIOS = master.getMasterAccountMoneyIos();
			
			try{
				sql = "select * from attach_account_items where cust_id="+login.getCustId()+" and activity_code='"
				      +code+"' and device_type='"+paramMap.get("deviceType")
				      + "' and is_show=1";
				DbUtil.selectOne(Config.ACCOUNTDBConfig, sql);
				hasReceive = true;
			}
			catch(Exception e){
				logger.info("银铃铛未被领取过");			
				hasReceive = false;
			}	
	    }	    
	    
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<AccountInfoData> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			if(!hasReceive){
				String sql = "SELECT * FROM `attach_account_activity_items` where "
					       + "activity_code='"+code+"' and attach_account_activity_id="+id;
				Map<String,Object> map = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql);
				int faceValue = Integer.parseInt(map.get("face_value").toString());
				
				sql = "select * from attach_account where cust_id="+login.getCustId();
				AttachAccount attach =DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
				
				sql = "select * from master_account where cust_id="+login.getCustId();
				MasterAccount master =DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, MasterAccount.class);
				
				if(deviceType.equalsIgnoreCase("Android")){
					dataVerifyManager.add(new ValueVerify<Integer>(attachM + faceValue, attach.getAttachAccountMoney()));
					dataVerifyManager.add(new ValueVerify<Integer>(masterM, master.getMasterAccountMoney()));
				}
				else{
					dataVerifyManager.add(new ValueVerify<Integer>(attachMIOS + faceValue, attach.getAttachAccountMoneyIos()));
					dataVerifyManager.add(new ValueVerify<Integer>(masterMIOS, master.getMasterAccountMoneyIos()));
				}				
			}
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify

			//如果用户已经领过活动奖励了，就不能再领取
			if(expectedCode.equalsIgnoreCase("0"))
			{
				//"亲，您已经领取过活动奖励啦!"
				expectedCode = "100034";
				dataVerifyResult = true;
			}
		}
		verifyResult(expectedCode);
	}
}
