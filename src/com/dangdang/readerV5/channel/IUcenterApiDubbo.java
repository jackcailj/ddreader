package com.dangdang.readerV5.channel;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.digital.model.Channel;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.enums.ChannelOwnerEnums;
import com.dangdang.ucenter.vo.UserBaseInfo;


public class IUcenterApiDubbo extends FixtureBase{
	protected static Logger log = Logger.getLogger(IUcenterApiDubbo.class);
	Channel channel;
	String channelId;
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IUcenterApi iUcenterApi = (IUcenterApi)factory.getBean("iUcenterApi");
	
	public UserBaseInfo getUserBaseInfo(String custId){
		Long _custId = Long.valueOf(custId);
		iUcenterApi.channelOwnerAuthorizationHandle(_custId,ChannelOwnerEnums.UNPASS);		
		UserBaseInfo userInfo = iUcenterApi.getUserBaseInfo(_custId);
		return userInfo;
	}
	
	public boolean verifyResult() throws Exception{	
		log.info("运行环境：" + env);
		if(env.equals(TestEnvironment.TESTING.toString())){				
//			if(channel.getChannelMonthlyStrategies().size()!=0){
//				dataVerifyManager.setCaseExpectResult(true);
//			}
//			log.info("verifyResult()验证结果："+ dataVerifyManager.dataVerify());
//			return dataVerifyManager.dataVerify(); 
			return true;
		}else
			return true;
	}	
	
	public static void main(String[] args){
		IUcenterApiDubbo dubbo = new IUcenterApiDubbo();
		UserBaseInfo user = dubbo.getUserBaseInfo("50098052");
		System.out.println("aabb"+user.getBarOwnerLevel());
		System.out.println("aabb"+user.getChannelOwner());
		System.out.println("aabb"+user.isCreateBar());
		System.out.println("aabb"+user.getCustImg());
		System.out.println("aabb"+user.getDisplayId());
		System.out.println("aabb"+user.getExperience());
		System.out.println("aabb"+user.getGender());
		System.out.println("aabb"+user.getIntegral());
		System.out.println("aabb"+user.getIntroduct());
		System.out.println("aabb"+user.getLevel());
		System.out.println("aabb"+user.getNickName());
		System.out.println("aabb"+user.getNickNameAll());
		System.out.println("aabb"+user.getPubCustId());		
	}
}
