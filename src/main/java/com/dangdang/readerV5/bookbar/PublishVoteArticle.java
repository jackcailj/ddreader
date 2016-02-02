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
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

public class PublishVoteArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	static String barId;
	
	public ReponseV2<PublishArticleResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PublishArticleResponse>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(barId==null){
			barId = Long.toString(
					BarMemberDb.getJoniedBarEmember(login.getCustId()).get((new Random()).nextInt(10)).getBarId());
		}
		if(paramMap.get("param")!=null&&paramMap.get("param").contains("\"barId\":FromDB")){
			String para = paramMap.get("param");
			paramMap.put("param", para.replace("FromDB", barId));
		}

	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			MediaDigest digest = MediaDigestDb.getMediaDigest(reponseResult.getData().getMediaDigestId());
			dataVerifyManager.add(new ExpressionVerify(digest!=null));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
