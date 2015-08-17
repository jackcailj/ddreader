package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarMembers;
import com.dangdang.readerV5.reponse.BarMembersResponse;
/**
 * 吧成员列表接口
 * @author wuhaiyan
 */
public class QueryBarMember  extends FixtureBase {	
	ReponseV2<BarMembersResponse>   reponseResult;
	String barId = "";
	
	public ReponseV2<BarMembersResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarMembersResponse>>(){});
	}
	@Override
	 public void setParameters(Map<String, String> params) throws Exception {
			super.setParameters(params);
			String sql = "select bar_id from bar where bar_status!=4 and  member_num > 0 ORDER BY rand() limit 1";
			barId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();	
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				paramMap.put("barId", barId);
			}
	 }
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
			reponseResult = getResult();
			if(reponseResult.getStatus().getCode() == 0){
				List<BarMembers> responseList = reponseResult.getData().getBarMembers();
				String sql = "select bm.bar_member_id, bm.cust_id, bm.member_status from bar_member as bm where bm.member_status!=4 "
				    	+ "and bm.bar_id="+barId+" ";
				List<Map<String, Object>>  lists = DbUtil.selectList(Config.BOOKBARDBConfig, sql);	
				List<BarMembers> memberList = new ArrayList<BarMembers>();
				for(int i=0; i<lists.size(); i++){	
					BarMembers member = new BarMembers();
					String custId = lists.get(i).get("cust_id").toString();
					Map<String, Object> map = DbUtil.selectOne(Config.UCENTERDBConfig, 
							"select cust_nickname, cust_img from login_record where cust_id="+custId);
					member.setBarMemberId(lists.get(i).get("bar_member_id").toString());
					member.setMemberStatus(lists.get(i).get("member_status").toString());
					member.setHeadPhoto(map.get("cust_img").toString());
					//判断nickName是不是手机号，如果是手机号将中间四位设置成*
					String nickName = map.get("cust_nickname").toString();
					String pattern = "^1[3|4|5|8][0-9]\\d{4,8}$";
					Matcher matcher = Pattern.compile(pattern, Pattern.DOTALL).matcher(nickName);
					if(matcher.find()){
						nickName = nickName.substring(0, 2) + "****" + nickName.substring(7, 10);
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
				dataVerifyManager.add(new ListVerify(memberList, responseList,true));
				super.dataVerify();
			}	
			else{
				dataVerifyResult = false;
				//TODO will add verify
			}
			verifyResult(expectedCode);
	}

}
