package com.dangdang.readerV5.activity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AttachAccountActivity;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.AttachAccountActivityDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Activity;
import com.dangdang.readerV5.reponse.GetActivityReponse;

/**
 * 
 * @author guohaiying
 * @date 2016年5月23日 上午11:17:27
 */
public class GetActivityByActivitycode extends FixtureBase{

	ReponseV2<GetActivityReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
    	super.doWork();
    	jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetActivityReponse>>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	AttachAccountActivity activityDb = AttachAccountActivityDb.getActivityMsg(paramMap.get("activityCode"));
        	Activity activity = jsonResult.getData().getActivity();
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityCode(), activityDb.getActivityCode()).setVerifyContent("验证activityCode！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityDesc(), activityDb.getActivityDesc()).setVerifyContent("验证activityDesc！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityEffectiveDate(), String.valueOf(activityDb.getActivityEffectiveDate().getTime())).setVerifyContent("验证activityEffectiveDate！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityGrantType(), activityDb.getActivityGrantType()).setVerifyContent("验证activityGrantType！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityName(), activityDb.getActivityName()).setVerifyContent("验证activityName！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityStartDate(), String.valueOf(activityDb.getActivityStartDate().getTime())).setVerifyContent("验证activityStartDate！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getActivityStatus(), String.valueOf(activityDb.getActivityStatus())).setVerifyContent("验证activityStatus！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getAttachAccountActivityId(), String.valueOf(activityDb.getAttachAccountActivityId())).setVerifyContent("验证attachAccountActivityId！"));
        	
        	String itemType;
        	if(activityDb.getItemType().equals("Android")||activityDb.getItemType().equals("android"))
        		itemType = "1";
        	else 
        		itemType = "2";
        	dataVerifyManager.add(new ValueVerify<String>(activity.getItemType(), itemType).setVerifyContent("验证itemType！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getNeedValidCode(), String.valueOf(activityDb.getNeedValidCode())).setVerifyContent("验证needValidCode！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getUserType(), String.valueOf(activityDb.getUserType())).setVerifyContent("验证userType！"));
        	dataVerifyManager.add(new ValueVerify<String>(activity.getVersionNo(), activityDb.getVersionNo()).setVerifyContent("验证versionNo！"));       	
        }
        super.dataVerify();
    }
}
