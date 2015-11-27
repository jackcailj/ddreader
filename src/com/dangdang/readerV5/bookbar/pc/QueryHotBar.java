package com.dangdang.readerV5.bookbar.pc;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarInfo;
import com.dangdang.readerV5.reponse.RecommendBar;

public class QueryHotBar  extends BarCommon {
	int defaultCnt= 5;
	String defaultType = "3";
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<RecommendBar> reponseResult = 
				JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<RecommendBar>>(){});
		if(reponseResult.getStatus().getCode() == 0){	
			if(paramMap.get("type").equals("3")){
				List<BarInfo> barInfo = reponseResult.getData().getBarList();
				verifyRecommendBar(barInfo, defaultType, defaultCnt);
			}
			else{
				//type=1,2 是扩展项，返回barList为空
				dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBarCnt(),0, false));
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
