package com.dangdang.readerV5.bookbar;

import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.enumeration.GradeExpRelationMap;
import com.dangdang.readerV5.reponse.Data;

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
	//加入吧，加经验和积分各5分
	//退出吧，减经验和积分各5分
	int experience = 0;
	int integral = 0;
	int account_experience;
	int account_integral;
	GradeExpRelationMap geMap = new GradeExpRelationMap();
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		numberNum = 0;
		String sql = null;
		if(paramMap.get("barId")!=null&&(paramMap.get("barId").equalsIgnoreCase("leave")
				||paramMap.get("barId").equalsIgnoreCase("Repeat Join"))){
			sql = "select b.bar_id, b.member_num, bm.member_status from bar as b left join bar_member as bm on b.bar_id=bm.bar_id "
					+ "where bm.member_status in (1,2,3) and b.bar_status!=4 and bm.cust_id="+login.getCustId()+
					" limit 20";
		}
		if(paramMap.get("barId")!=null&&(paramMap.get("barId").equalsIgnoreCase("Join")
				||paramMap.get("barId").equalsIgnoreCase("Repeat Leave"))){
			sql = "select bar_id,member_num from bar where bar_id not in (select bar_id from bar_member "
					+ "where cust_id="+login.getCustId()+") limit 20";
		}
		if(sql!=null){
			Map<String,Object> map = DbUtil.selectList(Config.BOOKBARDBConfig, sql).get((new Random()).nextInt(20));
			barId = map.get("bar_id").toString();	
		    paramMap.put("barId", barId);
		    numberNum = Integer.parseInt(map.get("member_num").toString());		
		    if(login!=null&&login.getCustId()!=null){
				sql = "SELECT * FROM `attach_account` where cust_id="+login.getCustId();
				AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
				account_experience = account.getAccountExperience();
				account_integral = account.getAccountIntegral();
			}
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
				//加入吧，加经验和积分各5分
				experience = 5;
				integral = 5;
				numberNum = numberNum+1;	
				//积分累积到一定数量，就有升级奖励积分
				account_experience = account_experience + experience;
				account_experience = account_experience + (geMap.get(account_experience)==null?0:geMap.get(account_experience));
				account_integral = account_integral + integral;
			}
			else{				
				//退出吧，成员数量减1		
				numberNum = numberNum-1;	
				//5.2版本中，退出吧，扣减积分，5.2之前的版本，不扣减
				//退出吧，减经验和积分各5分
//				experience = -5;
//				integral = -5;
//				account_experience = account_experience + experience;
//				account_integral = account_integral + integral;
			}
			sql = "SELECT * FROM `attach_account` where cust_id="+login.getCustId();
			AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
			dataVerifyManager.add(new ValueVerify<Integer>(bar.getMemberNum(),numberNum, false));
			dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountExperience(),
                    account_experience,false));
            dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountIntegral(),
                    account_integral, false));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
