package com.dangdang.readerV5.channel;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelOwnerDb;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.digital.model.Channel;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.vo.UserBaseInfo;

import fitnesse.slim.SystemUnderTest;

/**
 * 频道道长认证 0：无认证   1：已认证
 * @author guohaiying
 *
 */
public class IUcenterApiDubbo{
	protected static Logger log = Logger.getLogger(IUcenterApiDubbo.class);
	Channel channel;
	String channelId;
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IUcenterApi iUcenterApi = (IUcenterApi)factory.getBean("iUcenterApi");
	UserBaseInfo userInfo;
	
	@SystemUnderTest
	public ChannelOwnerDb db = new ChannelOwnerDb();
		
	public void getUserBaseInfo(String custId){
		if(env.equals(TestEnvironment.TESTING.toString())){	
			Long _custId = Long.valueOf(custId);	
			userInfo = iUcenterApi.getUserBaseInfo(_custId);
		}
	}
	
	public boolean verifyResult(String flag) throws Exception{	
		log.info("运行环境：" + env);
		if(env.equals(TestEnvironment.TESTING.toString())){	
			System.out.println("userInfo.getChannelOwner "+userInfo.getChannelOwner());
			if(Integer.valueOf(flag)==userInfo.getChannelOwner())
				return true;
			else 
				return false;
		}else
			return true;
	}	
	
	public static void main(String[] args){
		//测试点：认证通过账户50230557    认证不通过账户 50098052   未申请过认证的用户    个人账户   企业账户 
		IUcenterApiDubbo dubbo = new IUcenterApiDubbo();
		dubbo.getUserBaseInfo("50244453");
		//频道道长认证 0:无认证 1:已认证   //ucenter    login_record表
		// digest  channel_owner表
		System.out.println("ChannelOwner："+dubbo.userInfo.getChannelOwner());	
	}
}
