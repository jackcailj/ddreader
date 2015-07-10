package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ChannelSQL;
import com.dangdang.readerV5.reponse.ChannelArticleReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 文章详情页接口
 * @author guohaiying
 *
 */
public class getDigestDetail extends FixtureBase{
	
	ReponseV2<ChannelArticleReponse> reponseResult;
	
	@SystemUnderTest
	ChannelSQL sql = new ChannelSQL();
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelArticleReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			
		}
		return true;
	}
}
