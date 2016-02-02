package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.bookbar.BarMemberDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

public class PublishArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	static String barId;
	int publishCount=0;
	
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

	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			if(paramMap.get("actionType").equals("1")){
				publishCount++;
			}
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			MediaDigest digest = MediaDigestDb.getMediaDigest(reponseResult.getData().getMediaDigestId());
			if(paramMap.get("title")!=null){
				list1.add(digest.getTitle());
				list2.add(paramMap.get("title"));
			}			
			if(paramMap.get("content")!=null&&!(paramMap.get("content").isEmpty())){
				list1.add(digest.getCardRemark());			
				list2.add(paramMap.get("content"));
			}			
			if(paramMap.get("imgUrls")!=null&&!(paramMap.get("imgUrls").isEmpty())){
				list1.add(digest.getSmallPic1Path());
				list2.add(paramMap.get("imgUrls"));
			}
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
