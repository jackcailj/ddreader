/**
 * 
 */
package com.dangdang.readerV5.bookstore;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SpecialTopicHistoryReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * @author guohaiying
 *
 */
public class SpecialTopicHistory extends FixtureBase{
	
	ReponseV2<SpecialTopicHistoryReponse> reponseResult;
	   
	public ReponseV2<SpecialTopicHistoryReponse> getReponseResult() {
		return reponseResult;
	}
	 
	@SystemUnderTest
	//BookStoreTestEvnSQL sql = new BookStoreTestEvnSQL();
	 
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
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SpecialTopicHistoryReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证历史专题返回结果：");	
			//SpecialTopicHistoryReponse dbResponse = BookStoreTestEvnSQL.getSpecialTopicHistory(deviceType);
			//log.info(dbResponse.getCount());
			//log.info(dbResponse.getSpecialTopicList().get(0).getName());
			//dataVerifyManager.add(new ValueVerify<SpecialTopicHistoryReponse>(reponseResult.getData(), dbResponse, true));
		}
		return dataVerifyManager.dataVerify();
	}
}
