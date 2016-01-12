package com.dangdang.readerV5.comment;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2Base;

public class PraiseComment extends FixtureBase{
	String targetId = null;
	public ReponseV2Base getResult(){
		return JSONObject.parseObject(result.toString(), ReponseV2Base.class);
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);		
		String sql = null;
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("FromDB")){
			sql = "select target_id from comment where "
					   + ((Config.getEnvironment()== TestEnvironment.TESTING)?"is_delete=0 and ":"")+"status=1 "
					   + "and target_source="+paramMap.get("targetSource")+ " and target_id not in "
					   + "(SELECT target_id from praise_info where user_id="+login.getCustId()+") limit 10";			
			
		}
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("repeat")){
			sql = "select target_id from comment where "
					   + ((Config.getEnvironment()== TestEnvironment.TESTING)?"is_delete=0 and ":"")+"status=1 "
					   + "and target_source="+paramMap.get("targetSource")+ " and target_id in "
					   + "(SELECT target_id from praise_info where user_id="+login.getCustId()+") limit 10";
		}
		if(sql!=null){
			List<Map<String,Object>> list = DbUtil.selectList(Config.BSAECOMMENT, sql);
			targetId = list.get((new Random()).nextInt(list.size())).get("target_id").toString();	
			paramMap.put("targetId",targetId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2Base reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = "SELECT * from praise_info where target_id="+targetId+" and user_id="+login.getCustId()+""
					     + " and target_source="+paramMap.get("targetSource");	
			dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT, sql));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
