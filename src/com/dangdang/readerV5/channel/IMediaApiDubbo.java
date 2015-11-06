package com.dangdang.readerV5.channel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.api.IMediaApi;


import fitnesse.slim.SystemUnderTest;

/**
 * 获取图书包月频道信息Dubbo
 * @author guohaiying
 *
 */
public class IMediaApiDubbo extends FixtureBase{
	Long returnChannelId;
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IMediaApi iMediaApi = (IMediaApi)factory.getBean("iMediaApi");
	
	@SystemUnderTest
	public ChannelMonthlyStrategyDb Db = new ChannelMonthlyStrategyDb();
	
	//UserDeviceDb.getToken();
	
	public Long getChannelId(String token,String mediaId, String channelid) throws Exception{
		Long _mediaId = Long.valueOf(mediaId);
		Long _channelId = Long.valueOf(channelid);
		returnChannelId = iMediaApi.getMeidaMonthlyInfo(token,_mediaId,_channelId);	
		return  returnChannelId;
	}
	
	public static void main(String[] args){
		IMediaApiDubbo dubbo = new IMediaApiDubbo();
		try {
			Long l = dubbo.getChannelId("856095434f1f8ac8314863745e6d499b","1900089597","66");
			System.out.println("l: "+l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
