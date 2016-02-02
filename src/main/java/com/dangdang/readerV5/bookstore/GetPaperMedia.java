package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.bookstore.GetPaperMediaSQL;
import com.dangdang.readerV5.reponse.GetPaperMediaResponse;
import com.dangdang.readerV5.reponse.Media;

import fitnesse.slim.SystemUnderTest;

/**
 * 纸书详情页接口
 * @author guohaiying
 *
 */
public class GetPaperMedia extends FixtureBase{

	ReponseV2<GetPaperMediaResponse> reponseResult;
	
	@SystemUnderTest
	public GetPaperMediaSQL sql = new GetPaperMediaSQL();
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPaperMediaResponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证纸书返回信息：");	
			Media media = GetPaperMediaSQL.getPaperMediaWithProductid(paramMap.get("productId"));
			dataVerifyManager.add(new ValueVerify<Media>(reponseResult.getData().getMedia(), media, true));			
		}
		return dataVerifyManager.dataVerify();    
	 }
	
}
