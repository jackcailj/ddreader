package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.ucenter.UserInfoSql;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarMembers;
import com.dangdang.readerV5.reponse.BarMembersResponse;
import com.dangdang.readerV5.reponse.UserBaseInfo;
import com.dangdang.ucenter.meta.LoginRecord;
import com.dangdang.bookbar.meta.BarMember;

/**
 * 吧成员列表接口
 * @author wuhaiyan
 */
public class QueryBarMember  extends FixtureBase {	
	ReponseV2<BarMembersResponse>   reponseResult;
	
	public ReponseV2<BarMembersResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarMembersResponse>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
			reponseResult = getResult();
			if(reponseResult.getStatus().getCode() == 0){
				List<BarMembers> responseList = reponseResult.getData().getBarMembers();
				List<BarMember> lists = BarMemberDb.getBarRemember(paramMap.get("barId"));
				
				List<BarMembers> memberList = new ArrayList<BarMembers>();
				for(int i=0; i<lists.size(); i++){	
					BarMembers member = new BarMembers();
					UserBaseInfo userBaseInfo = new UserBaseInfo();
     				String custId = Long.toString(lists.get(i).getCustId());					
					member.setBarMemberId(lists.get(i).getBarMemberId().toString());
					member.setMemberStatus(Integer.toString(lists.get(i).getMemberStatus()));
					LoginRecord record = UserInfoSql.getUserInfoByCustId(custId);
					if(record!=null){
						userBaseInfo.setCustImg(record.getCustImg());
						//判断nickName是不是手机号，如果是手机号将中间四位设置成*
						String nickName = record.getCustNickname();
						String pattern = "^1[3|4|5|8][0-9]\\d{4,8}$";
						Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(nickName);
						if(matcher.find()){
							nickName = nickName.substring(0, 3) + "****" + nickName.substring(7, 11);
						}
						else{
							nickName = nickName.split("@")[0];
						}
						userBaseInfo.setNickName(nickName);
					}
					
					//5.3 验证吧主头衔		
					String level = BarCommon.getBarOwnerLevelFromDb(responseList.get(i).getUserBaseInfo().getPubCustId());
					userBaseInfo.setBarOwnerLevel(Integer.parseInt(level));
					member.setUserBaseInfo(userBaseInfo);			
					memberList.add(member);
				}

				if(lists.size() < 1000){
					Assert.assertEquals(responseList.size(), lists.size(),"接口返回的成员数量不等于数据库中查询到的成员数量");
				}
				else{
					//接口请求，一页默认返回1000个吧成员
					Assert.assertEquals(responseList.size(), 1000,"接口返回的成员数量不等于1000");
				}
				dataVerifyManager.add(new ListVerify(responseList,memberList,true));
				super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
	}

}
