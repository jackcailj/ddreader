package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.channel.ChannelSQL;
import com.dangdang.readerV5.reponse.ChannelResponse;

import fitnesse.slim.SystemUnderTest;

public class Channel extends FixtureBase{
	
	ReponseV2<ChannelResponse> reponseResult;	
	
	@SystemUnderTest
	public ChannelSQL sql = new ChannelSQL();
	
	public void setParamWithKeyAndValue(String key, String value){
		paramMap.put(key, value);
	}

	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelResponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
		
			//验证json中返回的字段值
			log.info("验证json返回的字段值");
			ChannelResponse dbResponse = ChannelSQL.getChannel(login.getCustId(), paramMap.get("cId"));
		    dataVerifyManager.add(new ValueVerify<ChannelResponse>(reponseResult.getData(), dbResponse, true));
		}
						
		return dataVerifyManager.dataVerify();      	
	}
	
}
