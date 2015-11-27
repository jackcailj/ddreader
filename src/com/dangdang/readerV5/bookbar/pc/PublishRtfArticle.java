package com.dangdang.readerV5.bookbar.pc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.AttachAccountDb;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.enumeration.GradeExpRelationMap;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

public class PublishRtfArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	static String barId;
	static int publishCount=0;
	int experience = 10;
	int integral = 10;
	int account_experience;
	int account_integral;
	GradeExpRelationMap geMap = new GradeExpRelationMap();
	
	public ReponseV2<PublishArticleResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PublishArticleResponse>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		if(publishCount==4){
			//四次发帖成功后，等待90秒
			//防刷帖机制：60秒内发帖不能超过三次，第四次发帖，会提示“发帖速度太快，请休息一会儿吧”			
			Thread.sleep(1000*90);
			publishCount = 0;
		}
		super.setParameters(params);
		if(barId==null){
			barId = Long.toString(
					BarMemberDb.getJoniedBarEmember(login.getCustId()).get((new Random()).nextInt(10)).getBarId());
		}
		if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
			paramMap.put("barId",barId);
		}
		if(login!=null&&login.getCustId()!=null){
			AttachAccount account = AttachAccountDb.getAttachAccount(login.getCustId());
			account_experience = account.getAccountExperience();
			account_integral = account.getAccountIntegral();
		}
		//不定时内，发帖内容相同三次以上，会有防刷帖提示，为保证测试用例能多次运行，故在此确保每次运行用例时发帖内容每次都不一样
		String content = paramMap.get("content");
		if(content!=null&&!(content.isEmpty())){
			if(content.startsWith("<p>")&&!content.startsWith("<p><img")){	
				paramMap.put("content", "<p>"+Util.getRandomString(5)+content.substring(3, content.length()-1));
			}
			if(!content.startsWith("<p>")){	
				paramMap.put("content", Util.getRandomString(5)+content);
			}			
		}

	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			if(paramMap.get("actionType").equals("1")){
				publishCount++;
				//发帖，经验值，积分增加
				AttachAccount account = AttachAccountDb.getAttachAccount(login.getCustId());
				account_experience = account_experience + experience;
				account_experience = account_experience + (geMap.get(account_experience)==null?0:geMap.get(account_experience));
				dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountExperience(),
						account_experience,false));
	            dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountIntegral(),
	                    account_integral+integral, false));
	            account_experience = account_experience + (geMap.get(account_experience)==null?0:geMap.get(account_experience));

			}
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			MediaDigest digest = MediaDigestDb.getMediaDigest(reponseResult.getData().getMediaDigestId());
			if(paramMap.get("title")!=null){
				list1.add(digest.getTitle());
				list2.add(paramMap.get("title"));
			}			
			if(paramMap.get("content")!=null&&!(paramMap.get("content").isEmpty())){
				list1.add("<p>"+digest.getCardRemark()+"</p>");			
				list2.add(paramMap.get("content"));
			}		
			if(paramMap.get("content")!=null&&paramMap.get("content").contains("<img src=")){
				list1.add(digest.getSmallPic1Path());
				list2.add(paramMap.get("content").split("\"")[0]);
			}
//			if(paramMap.get("imgUrls")!=null&&!(paramMap.get("imgUrls").isEmpty())){
//				list1.add(digest.getSmallPic1Path());
//				list2.add(paramMap.get("imgUrls"));
//			}
			dataVerifyManager.add(new ListVerify(list1, list2,false));	
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
