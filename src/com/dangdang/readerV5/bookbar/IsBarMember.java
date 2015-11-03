package com.dangdang.readerV5.bookbar;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.IsBarMemberData;

public class IsBarMember  extends FixtureBase {
	ReponseV2<IsBarMemberData>   reponseResult;
	String memberStatus =null;
	Boolean isBarMember = null;
	 
	public ReponseV2<IsBarMemberData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<IsBarMemberData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		String sql = null;
		if(paramMap.get("barId")!=null&&paramMap.get("barId").equalsIgnoreCase("true")){
			sql = "select b.bar_id, bm.member_status from bar as b left join bar_member as bm on b.bar_id=bm.bar_id "
					+ "where bm.member_status in (1,2,3) and b.bar_status!=4 and bm.cust_id="+login.getCustId()+
					" limit 1";
			//TODO remove order by rand, will do other updates
			isBarMember = true; 
		}
		if(paramMap.get("barId")!=null&&paramMap.get("barId").equalsIgnoreCase("false")){
			sql = "select bar_id from bar where bar_id not in (select bar_id from bar_member "
					+ "where cust_id="+login.getCustId()+") limit 1";
			//TODO remove order by rand, will do other updates
			isBarMember = false; 
		}
		if(sql!=null){
			String barId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();	
		    paramMap.put("barId", barId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			dataVerifyManager.add(new ValueVerify<Boolean>(isBarMember, reponseResult.getData().getIsBarMember(),false));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
