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
     				String custId = Long.toString(lists.get(i).getCustId());
					LoginRecord record = UserInfoSql.getUserInfoByCustId(custId);
					member.setBarMemberId(lists.get(i).getBarMemberId().toString());
					member.setMemberStatus(Integer.toString(lists.get(i).getMemberStatus()));
					member.setHeadPhoto(record.getCustImg());
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
					member.setNickName(nickName);
					//返回信息里的custid是加密的，为了便于下边比较，做如下设置
					member.setCustId(responseList.get(i).getCustId());
					memberList.add(member);
				}
//				for(BarMembers response:responseList){
//					int index = response.getHeadPhoto().lastIndexOf("?");
//					response.setHeadPhoto(response.getHeadPhoto().substring(0, index));
//				}
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
