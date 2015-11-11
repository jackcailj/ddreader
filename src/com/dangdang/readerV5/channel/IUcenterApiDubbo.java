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
import com.dangdang.ucenter.api.service.IUcenterApi;

import fitnesse.slim.SystemUnderTest;

public class IUcenterApiDubbo extends FixtureBase{
	protected static Logger log = Logger.getLogger(IChannelApiDubbo.class);
	Channel channel;
	String channelId;
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IUcenterApi iUcenterApi = (IUcenterApi)factory.getBean("iUcenterApi");
	
//	@SystemUnderTest
//	public ChannelMonthlyStrategyDb db = new ChannelMonthlyStrategyDb();
//	
//	public Channel getChannel(String channelId){
//		this.channelId = channelId;
//		Long _channelId = Long.valueOf(channelId);
//		channel = iChannelApi.getChannel(_channelId);
//		if(channel==null) 
//			return null;
//		else 
//			return channel;
//	}
//	
//	public boolean verifyResult() throws Exception{	
//		log.info("运行环境：" + env);
//		if(env.equals(TestEnvironment.TESTING.toString())){				
//			if(channel.getChannelMonthlyStrategies().size()!=0){
//				dataVerifyManager.setCaseExpectResult(true);
//				List<com.dangdang.digital.model.ChannelMonthlyStrategy> strategy = channel.getChannelMonthlyStrategies();
//				List<ChannelMonthlyStrategy> list = db.getChannelMonthlyStrategy(channelId);
//				for(int i=0; i<strategy.size(); i++){
//					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getId(), list.get(i).getId()).setVerifyContent("验证Id"));
//					dataVerifyManager.add(new ValueVerify<Integer>(strategy.get(i).getType(), list.get(i).getType()).setVerifyContent("验证Type"));
//					dataVerifyManager.add(new ValueVerify<String>(strategy.get(i).getName(), list.get(i).getName()).setVerifyContent("验证Name"));
//					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getAndroid(), list.get(i).getAndroid()).setVerifyContent("验证Android"));
//					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getIos(), list.get(i).getIos()).setVerifyContent("验证Ios"));
//					dataVerifyManager.add(new ValueVerify<Long>(strategy.get(i).getOriginalPrice(), list.get(i).getOriginalPrice()).setVerifyContent("验证OriginalPrice"));
//					
//				}
//			}
//			log.info("verifyResult()验证结果："+ dataVerifyManager.dataVerify());
//			return dataVerifyManager.dataVerify(); 
//		}else
//			return true;
//	}
//	
//	public boolean verifyIsAllowMonthly(String result) throws Exception {
//		ValueVerify verify;	
//		if(env.equals(TestEnvironment.TESTING.toString())){			
//			int  _result= channel.getIsAllowMonthly();
//			verify = new ValueVerify<Integer>(_result,Integer.valueOf(result));
//			log.info("verifyIsAllowMonthly()验证结果："+ verify.dataVerify());
//			return verify.dataVerify();
//		}else 
//			return true;
//	}
//	
}
