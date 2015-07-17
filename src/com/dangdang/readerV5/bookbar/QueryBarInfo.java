package com.dangdang.readerV5.bookbar;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarInfo;
import com.dangdang.readerV5.reponse.BarInfoResponse;

public class QueryBarInfo extends FixtureBase{	
    ReponseV2<BarInfoResponse>   reponseResult;
    String barId1 = "";
    String barId2 = "";
    
	public ReponseV2<BarInfoResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarInfoResponse>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
			super.setParameters(params);
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				String sql = "select bar_id from bar where bar_status!=4 ORDER BY rand() limit 1";
				barId1 = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();	
				paramMap.put("barId", barId1);
			}
			if(paramMap.get("objectId")!=null&&paramMap.get("objectId").equals("FromDB")){
				String sql = "select br.bar_id, br.object_id from bar_relation as br left join bar as b on br.bar_id=b.bar_id "
						+ "where b.bar_status!=4 ORDER BY rand() limit 1";
				Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
				barId2 = map.get("bar_id").toString();	
				String objectId = map.get("object_id").toString();	
				paramMap.put("objectId", objectId);
			}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String custId = login.getCustId();
			String sql = null;
			BarInfo info = new BarInfo();
			if(paramMap.get("barId")!=null&&(!paramMap.get("barId").isEmpty())){
				sql = "select * from bar where bar_status!=4 and bar_id ="+barId1;
			}
			if(paramMap.get("objectId")!=null&&(!paramMap.get("objectId").isEmpty())&&paramMap.get("barId")==null){
				sql = "select * from bar where bar_status!=4 and bar_id ="+barId2;
			}
			Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);
			info.setBarDesc(bar.getBarDesc());
			info.setBarId(bar.getBarId().toString());
			info.setBarImgUrl(bar.getBarImgUrl());
			info.setBarName(bar.getBarName());
			info.setBarType(Integer.toString(bar.getBarType()));
			info.setMemberNum(bar.getMemberNum().toString());
			//如果抛出空指针异常，该用户从未加入过该吧，memberStatus为4（非吧成员）
			try{
				sql = "select * from bar_member where cust_id="+custId+" and bar_id ="+bar.getBarId().toString();
				BarMember member = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, BarMember.class);
				info.setMemberStatus(Integer.toString(member.getMemberStatus()));
			}
			catch(NullPointerException e){
				info.setMemberStatus("4");
			}	
			dataVerifyManager.add(new ValueVerify(info, reponseResult.getData().getBar(),true));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
	
	
}
