package com.dangdang.readerV5.channel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.api.IMediaApi;
import com.dangdang.db.digital.channel.ChannelMonthlySQL;

import fitnesse.slim.SystemUnderTest;

/**
 * 获取图书包月频道信息Dubbo
 * @author guohaiying
 *
 */
public class IMediaApiDubbo extends FixtureBase{
	
	@SystemUnderTest
	public ChannelMonthlySQL sql = new ChannelMonthlySQL();
	
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IMediaApi iMediaApi = (IMediaApi)factory.getBean("iMediaApi");
	
	Long returnChannelId;
	public void getWithTokenAndMediaAndChannelid(String token,String mediaId, String channelid) throws Exception{
		Long _mediaId = Long.valueOf(mediaId);
		Long _channelId = Long.valueOf(channelid);
		returnChannelId = iMediaApi.getMeidaMonthlyInfo(token,_mediaId,_channelId);	
	}
	
	public boolean verifyChannelId(String expected) throws Exception{
		dataVerifyManager.setCaseExpectResult(true);	
		if(expected.equals(null))
			dataVerifyManager.add(new ValueVerify<String>(String.valueOf(returnChannelId),null));
		else{
			
		}
		return dataVerifyManager.dataVerify();
	}


}
