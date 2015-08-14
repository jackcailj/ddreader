package com.dangdang.readerV5.channel;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;

import com.dangdang.digital.api.IChannelApi;
import com.dangdang.digital.channel.ChannelMonthlySQL;
import com.dangdang.digital.model.Channel;
import com.dangdang.digital.model.ChannelMonthlyStrategy;

import fitnesse.slim.SystemUnderTest;

/**
 * 获取频道包月策略信息dubbo
 * @author guohaiying
 *
 */
public class IChannelApiDubbo extends FixtureBase{
	
	@SystemUnderTest
	public ChannelMonthlySQL sql = new ChannelMonthlySQL();
	
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IChannelApi iChannelApi = (IChannelApi)factory.getBean("iChannelApi");
	Channel channel;
	String channelId;
	
	public Channel getChannel(String channelId){
		this.channelId = channelId;
		Long _channelId = Long.valueOf(channelId);
		channel = iChannelApi.getChannel(_channelId);
		if(channel==null) return null;
		else return channel;
	}
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);	
		if(channel.getChannelMonthlyStrategies().size()!=0){						
			List<ChannelMonthlyStrategy> strategy = channel.getChannelMonthlyStrategies();
			dataVerifyManager.add(new ListVerify(strategy,ChannelMonthlySQL.getChannel(channelId), true));
		}
		return dataVerifyManager.dataVerify();
	}
	
	public boolean verifyIsAllowMonthly(String result) throws Exception {
		dataVerifyManager.setCaseExpectResult(true);	
		int  _result= channel.getIsAllowMonthly();
		dataVerifyManager.add(new ValueVerify<Integer>(_result,Integer.valueOf(result)));
		return dataVerifyManager.dataVerify();
	}
	
	public static void main(String[] args){
		IChannelApiDubbo ic = new IChannelApiDubbo();
		Channel c = ic.getChannel("8");
		boolean b = false;
		try {
			b = ic.verifyResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c.getIsAllowMonthly());
	}
}
