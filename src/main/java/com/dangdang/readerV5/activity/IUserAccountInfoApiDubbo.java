package com.dangdang.readerV5.activity;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelMonthlyStrategyDb;
import com.dangdang.bi.api.IUserAccountInfoApi;

import fitnesse.slim.SystemUnderTest;

/**
 * 判断是否为新设备
 * 判断是否为新用户
 * 243 bi库  bi_user_account、bi_mongo_user_null_temp表
 * @author guohaiying
 * @date 2016年4月27日 下午6:23:08
 */
public class IUserAccountInfoApiDubbo {
	protected static Logger log = Logger.getLogger(IUserAccountInfoApiDubbo.class);
	String env = Config.getEnvironment().toString();
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IUserAccountInfoApi iUserAccountInfoApi = (IUserAccountInfoApi)factory.getBean("userAccountInfoApi");
	
	@SystemUnderTest
	public ChannelMonthlyStrategyDb db = new ChannelMonthlyStrategyDb();
	
	public boolean checkNewForDevice(String deviceSerialNo){
		return iUserAccountInfoApi.checkNewForDevice(deviceSerialNo);
	}
	
	public boolean checkNewForCust(String custId){
		Long _custId = Long.valueOf(custId);
		return iUserAccountInfoApi.checkNewForCust(_custId);
	}
	
	public static void main(String[] args){
		IUserAccountInfoApiDubbo dubbo = new IUserAccountInfoApiDubbo();
		boolean device = dubbo.checkNewForDevice("867676026641828");
		System.out.println("device: " + device);
		
		boolean custId = dubbo.checkNewForCust("50245135");
		System.out.println("custId: " + custId);
	}

}
