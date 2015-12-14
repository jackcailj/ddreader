package com.dangdang.readerV5.purchase;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaChapterDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaChapter;
import com.dangdang.readerV5.reponse.BuyInfoData;

public class GetBatchBuyInfo extends FixtureBase{
	ReponseV2<BuyInfoData>   reponseResult;
	String chapterId = null;
	 
	public ReponseV2<BuyInfoData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BuyInfoData>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			chapterId = paramMap.get("chapterId").toString();
			List<MediaChapter> ch = MediaChapterDb.GetBookChapterLast(chapterId);
			String endId = Long.toString(ch.get(Integer.parseInt(paramMap.get("chapterCount").toString())-1).getId());
			long price = MediaChapterDb.GetSumOfChapters(chapterId, endId);
			dataVerifyManager.add(new ValueVerify<Long>(reponseResult.getData().getNeedPay(), price,false));
		super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}


}
