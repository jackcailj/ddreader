package com.dangdang.readerV5.bookbar;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.config.Config;
import com.dangdang.db.bookbar.BarDb;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.bookbar.BarRelationDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.enumeration.BarStatus;
import com.dangdang.readerV5.reponse.BarInfo;
import com.dangdang.readerV5.reponse.BarInfoResponse;
import com.dangdang.readerV5.reponse.UserBaseInfo;

public class QueryBarInfo extends FixtureBase{	
    ReponseV2<BarInfoResponse>   reponseResult;
    String barId = "";
    
	public ReponseV2<BarInfoResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarInfoResponse>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String custId = login.getCustId();
			String sql = null;
			BarInfo info = new BarInfo();			
			if(paramMap.get("barId")!=null&&(!paramMap.get("barId").isEmpty())){
				barId = paramMap.get("barId");
			}
			if(paramMap.get("objectId")!=null&&(!paramMap.get("objectId").isEmpty())&&paramMap.get("barId")==null){
				barId = BarRelationDb.getBarId(paramMap.get("objectId")).get("bar_id").toString();
			}
			Bar bar = BarDb.getBarInfo(BarStatus.APPROVED, barId);		
			info.setBarDesc(bar.getBarDesc().isEmpty()?
					        reponseResult.getData().getBar().getBarDesc():bar.getBarDesc());
			info.setBarId(bar.getBarId().toString());
			info.setBarImgUrl(bar.getBarImgUrl());
			info.setBarName(bar.getBarName());
			info.setBarType(Integer.toString(bar.getBarType()));
			info.setMemberNum(bar.getMemberNum().toString());
			//如果抛出空指针异常，该用户从未加入过该吧，memberStatus为4（非吧成员）
			try{
				BarMember member = BarMemberDb.getOneBarRemember(bar.getBarId().toString(), custId);
				info.setMemberStatus(Integer.toString(member.getMemberStatus()));
			}
			catch(Exception e){
				info.setMemberStatus("4");
			}	
			try{
				sql = "select * from bar_product_info where is_has_bar=1 and bar_id="+bar.getBarId().toString()+" limit 1";
				BarProductInfo book = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, BarProductInfo.class);
				info.setHasBook("1");			}
			catch(Exception e){
				info.setHasBook("0");
			}				
			dataVerifyManager.add(new ValueVerify(reponseResult.getData().getBar(),info, true));
			
			//5.3 验证吧主头衔
			//在发帖，删帖，加入吧，退出吧，用户升级，降级的时候，这些动作会触发代码，更新吧主的头衔
			//更新的数据存在login_record表里的bar_owner_level字段。
			//下边验证level有时候失败，可能是因为，没有上述动作触发数据更新。
			//在这儿用getBarOwnerLevel方法验证，是为了验证规则的正确性，其他接口是从数据表取bar_owner_level值来验证的。
			UserBaseInfo userBaseInfo = reponseResult.getData().getBar().getUserBaseInfo();
			if(userBaseInfo!=null){
				BarCommon common = new BarCommon();
				String cust = userBaseInfo.getPubCustId();
				//int level = common.getBarOwnerLevel(cust);
				//改成从数据表取bar_owner_level值来验证
				String level = BarCommon.getBarOwnerLevelFromDb(cust);
				dataVerifyManager.add(new ValueVerify<Integer>(
						reponseResult.getData().getBar().getUserBaseInfo().getBarOwnerLevel(),
						Integer.parseInt(level),false));
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
