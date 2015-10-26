package com.dangdang.readerV5.bookstore;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.FreeForLimitedReponse;

import fitnesse.slim.SystemUnderTest;

public class FreeForLimited extends FixtureBase{
	ReponseV2<FreeForLimitedReponse> reponseResult;   
	 
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	 
	String deviceType;
	@Override 
	public void setParameters(Map<String, String> params) throws Exception{
		deviceType = params.get("deviceType");
		super.setParameters(params);
		paramMap.put("deviceType", deviceType);
		log.info(params.get("deviceType"));
	}
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<FreeForLimitedReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证限免返回结果：");	
			int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			//FreeForLimitedReponse dbResponse = BookStoreTestEvnSQL.getFreeForLimitedReponse(paramMap.get("channelType") +"_"+paramMap.get("columnType"), size);
			//log.info(dbResponse.getCount());
			//dataVerifyManager.add(new ValueVerify<FreeForLimitedReponse>(reponseResult.getData(), dbResponse, true));
		}
		return dataVerifyManager.dataVerify();
	}
	
}
