package com.dangdang.readerV5.bookstore;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelBgImgDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetBackgroundImgListResponse;
import com.dangdang.readerV5.reponse.GetMediaCategorySaleTopnResponse;
import com.dangdang.readerV5.reponse.UrlList;

public class GetMediaCategorySaleTopn extends FixtureBase{
	ReponseV2<GetMediaCategorySaleTopnResponse> jsonResult;
	
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMediaCategorySaleTopnResponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){
			//List<UrlList>  actual = ChannelBgImgDb.getBackImg(paramMap.get("type"));
			//dataVerifyManager.add(new ListVerify(jsonResult.getData().getUrlList(),actual, true));           
		}
		super.dataVerify();
	}
	
}
