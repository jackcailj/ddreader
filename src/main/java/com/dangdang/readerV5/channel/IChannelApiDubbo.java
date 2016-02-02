package com.dangdang.readerV5.channel;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.ValueVerify;

import com.dangdang.digital.api.IChannelApi;
import com.dangdang.digital.meta.ChannelMonthlyStrategy;
import com.dangdang.digital.model.Channel;

import fitnesse.slim.SystemUnderTest;

/**
 * 获取频道包月策略信息dubbo
 * @author guohaiying
 *
 */
public class IChannelApiDubbo extends FixtureBase{
	protected static Logger log = Logger.getLogger(IChannelApiDubbo.class);
	Channel channel;
	String channelId;
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IChannelApi iChannelApi = (IChannelApi)factory.getBean("iChannelApi");
	
	@SystemUnderTest
	public ChannelMonthlyStrategyDb db = new ChannelMonthlyStrategyDb();
	
	public Channel getChannel(String channelId){
		this.channelId = channelId;
		Long _channelId = Long.valueOf(channelId);
		channel =iChannelApi.getChannel(_channelId);
		//channel = iChannelApi.getChannel(_channelId, null, 1).listId;
		if(channel==null) 
			return null;
		else 
			return channel;
	}
	
	public boolean verifyResult() throws Exception{	
		log.info("运行环境：" + env);
		if(env.equals(TestEnvironment.TESTING.toString())){				
			if(channel.getChannelMonthlyStrategies().size()!=0){
				dataVerifyManager.setCaseExpectResult(true);
				List<com.dangdang.digital.model.ChannelMonthlyStrategy> strategy = channel.getChannelMonthlyStrategies();
				List<ChannelMonthlyStrategy> list = db.getChannelMonthlyStrategy(channelId);
				for(int i=0; i<strategy.size(); i++){
					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getId(), list.get(i).getId()).setVerifyContent("验证Id"));
					dataVerifyManager.add(new ValueVerify<Integer>(strategy.get(i).getType(), list.get(i).getType()).setVerifyContent("验证Type"));
					dataVerifyManager.add(new ValueVerify<String>(strategy.get(i).getName(), list.get(i).getName()).setVerifyContent("验证Name"));
					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getAndroid(), list.get(i).getAndroid()).setVerifyContent("验证Android"));
					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getIos(), list.get(i).getIos()).setVerifyContent("验证Ios"));
					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getOriginalPrice(), list.get(i).getOriginalPrice()).setVerifyContent("验证OriginalPrice"));
					//dataVerifyManager.add(new ValueVerify<Integer>(strategy.get(i).getPlatform(), list.get(i).getPlatform()).setVerifyContent("验证Platform"));
					//dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getNewPrice(), list.get(i).getNewPrice()).setVerifyContent("验证NewPrice"));
					//dataVerifyManager.add(new ValueVerify<Integer>(strategy.get(i).getMaxTimes(), list.get(i).getMaxTimes()).setVerifyContent("验证MaxTimes"));
					//dataVerifyManager.add(new ValueVerify<Integer>(strategy.get(i).getStrategyDays(), list.get(i).getStrategyDays()).setVerifyContent("验证StrategyDays"));
				}
			}
			log.info("verifyResult()验证结果："+ dataVerifyManager.dataVerify());
			return dataVerifyManager.dataVerify(); 
		}else
			return true;
	}
	
	public boolean verifyIsAllowMonthly(String result) throws Exception {
		ValueVerify verify;	
		if(env.equals(TestEnvironment.TESTING.toString())){			
			int  _result= channel.getIsAllowMonthly();
			verify = new ValueVerify<Integer>(_result,Integer.valueOf(result));
			log.info("verifyIsAllowMonthly()验证结果："+ verify.dataVerify());
			return verify.dataVerify();
		}else 
			return true;
	}
	
	public static void main(String[] args){
		IChannelApiDubbo ic = new IChannelApiDubbo();
		Channel c = ic.getChannel("17");
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getId());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getType());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getName());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getAndroid());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getIos());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getOriginalPrice());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getChannelId());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getPlatform());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getNewPrice());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getMaxTimes());
		System.out.println("aaaaaachannel "+ c.getChannelMonthlyStrategies().get(0).getStrategyDays());
		boolean b = false;
		try {
			b = ic.verifyResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(c.getIsAllowMonthly());
	}
}
