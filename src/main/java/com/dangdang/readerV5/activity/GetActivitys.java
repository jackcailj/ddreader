package com.dangdang.readerV5.personal_center;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ActivitiesData;
import com.dangdang.readerV5.reponse.Activity;

/**
 * 个人中心活动推广接口
 */
public class GetActivitys extends FixtureBase{
	public ReponseV2<ActivitiesData> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ActivitiesData>>(){});
	}
	String versionNo;
	String channelId;
	String deviceType;
	String itemType;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		versionNo = params.get("clientVersionNo").toString();
	    channelId = params.get("channelId").toString();
	    deviceType = params.get("deviceType").toString();
	    super.setParameters(params);
	    paramMap.put("clientVersionNo", versionNo);
	    paramMap.put("channelId", channelId);
	    paramMap.put("deviceType", deviceType);	    
	    if(deviceType.equalsIgnoreCase("Android")){
	    	itemType = "Android";
	    }
	    else{
	    	itemType = "IOS";
	    }
	    
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<ActivitiesData> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String date = df.format(new Date());
			List<Activity> list = reponseResult.getData().getActivitys();
			//user_type: 活动面向用户0:新用户,1:老用户,2:所有用户
			String sql = "SELECT * FROM attach_account_activity where version_no='"+versionNo+"'"
					   + "and channel_id="+channelId+" and activity_status=1 and item_type='"+itemType+"' "
					   + "and activity_effective_date>'"+date+"' and attach_account_activity_id in "
					   + "(SELECT attach_account_activity_id from attach_account_activity_items where user_type=1 or user_type=2)";
			List<Map<String,Object>> activity = DbUtil.selectList(Config.ACCOUNTDBConfig, sql);
			int size = activity.size();			
			if(login!=null){
				for(int i=0; i<activity.size(); i++){
					try{
						sql = "select * from attach_account_items where cust_id="+login.getCustId()+" and activity_code='"
						      +activity.get(i).get("activity_code")+"' and device_type='"+paramMap.get("deviceType")
						      + "' and is_show=1";
						DbUtil.selectOne(Config.ACCOUNTDBConfig, sql);
						size--;
						activity.remove(i);
					}
					catch(Exception e){
						logger.info("银铃铛未被领取过");						
					}					
				}
			}
			
			for(int i=0; i<size; i++){
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				list1.add(activity.get(i).get("attach_account_activity_id").toString());
				list1.add(activity.get(i).get("activity_code").toString());
				list1.add(activity.get(i).get("activity_name").toString());
				list1.add(activity.get(i).get("version_no").toString());
				list1.add(activity.get(i).get("channel_id").toString());
				
				list2.add(list.get(i).getAttachAccountActivityId());
				list2.add(list.get(i).getActivityCode());
				list2.add(list.get(i).getActivityName());
				list2.add(list.get(i).getVersionNo());
				list2.add(list.get(i).getChannelId());		
				dataVerifyManager.add(new ValueVerify(list1,list2));
			}
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
