package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ChannelSQL;
import com.dangdang.readerV5.reponse.ChannelResponse;

import fitnesse.slim.SystemUnderTest;

public class Channel extends FixtureBase{
	
	ReponseV2<ChannelResponse> reponseResult;
	
	//@SystemUnderTest
	//public Channelsub channelSub = new Channelsub();
	
	@SystemUnderTest
	public ChannelSQL sql = new ChannelSQL();

	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelResponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
		
			//验证json中返回的字段值
			log.info("验证json返回的字段值");
			ChannelResponse dbResponse = ChannelSQL.getChannel(Integer.valueOf(paramMap.get("cId")));
			dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
						
		}
		return dataVerifyManager.dataVerify();      	
	}
	
	public static void main(String[] args){
		String result="{\"data\":{\"channel\":{\"bookList\":{\"booklistId\":16},\"channelId\":6,\"description\":\"好拽的频道啊\",\"hasArtical\":1,\"icon\":\"jpg\",\"isSub\":0,\"ownder\":\"动物园\",\"subNumber\":6,\"title\":\"测试一下www\"},\"currentDate\":\"2015-07-03 10:13:29\",\"systemDate\":\"1435889609747\"},\"status\":{\"code\":0},\"systemDate\":1435889609747}";
		JSONObject.parseObject(result,new TypeReference<ReponseV2<ChannelResponse>>(){});
	}

}
