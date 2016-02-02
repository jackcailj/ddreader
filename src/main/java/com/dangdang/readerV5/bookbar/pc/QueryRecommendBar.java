package com.dangdang.readerV5.bookbar.pc;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarInfo;
import com.dangdang.readerV5.reponse.RecommendBar;

public class QueryRecommendBar extends BarCommon {	
	int defaultCnt= 8;
	String defaultType = "1";
	QueryHotBar queryHotBar = new QueryHotBar();
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<RecommendBar> reponseResult = 
				JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<RecommendBar>>(){});
		if(reponseResult.getStatus().getCode() == 0){			
			List<BarInfo> barInfo = reponseResult.getData().getBarList();
			verifyRecommendBar(barInfo, defaultType, defaultCnt);
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
