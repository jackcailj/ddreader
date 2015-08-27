package com.dangdang.readerV5.bookbar;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Data;

import fitnesse.slim.SystemUnderTest;
/**
 * 加入吧，退出吧，申请吧主接口
 * @author wuhaiyan
 */
public class BarMember extends FixtureBase {
	public ReponseV2<Data> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	int numberNum;
	String barId;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		numberNum = 0;
		String sql = null;
		if(paramMap.get("barId")!=null&&(paramMap.get("barId").equalsIgnoreCase("leave")
				||paramMap.get("barId").equalsIgnoreCase("Repeat Join"))){
			sql = "select b.bar_id, b.member_num, bm.member_status from bar as b left join bar_member as bm on b.bar_id=bm.bar_id "
					+ "where bm.member_status in (1,2,3) and b.bar_status!=4 and bm.cust_id="+login.getCustId()+
					" order by rand() limit 1";
		}
		if(paramMap.get("barId")!=null&&(paramMap.get("barId").equalsIgnoreCase("Join")
				||paramMap.get("barId").equalsIgnoreCase("Repeat Leave"))){
			sql = "select bar_id,member_num from bar where bar_id not in (select bar_id from bar_member "
					+ "where cust_id="+login.getCustId()+") order by rand() limit 1";
		}
		if(sql!=null){
			Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
			barId = map.get("bar_id").toString();	
		    paramMap.put("barId", barId);
		    numberNum = Integer.parseInt(map.get("member_num").toString());	
		    
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<Data> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			String sql = "select * from bar where bar_id="+barId;
			Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);
			if(paramMap.get("actionType").equals("1")){
				//加入吧，成员数量加1
				dataVerifyManager.add(new ValueVerify<Integer>(numberNum+1, bar.getMemberNum(),false));
			}
			else{
				//退出吧，成员数量减1
				dataVerifyManager.add(new ValueVerify<Integer>(numberNum-1, bar.getMemberNum(),false));
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
