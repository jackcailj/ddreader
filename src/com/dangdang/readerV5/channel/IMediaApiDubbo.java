package com.dangdang.readerV5.channel;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.digital.api.IMediaApi;
import com.dangdang.digital.meta.MediaBooklistDetail;

/**
 * Dubbo
 * @author guohaiying
 *
 */
public class IMediaApiDubbo extends FixtureBase{
	protected static Logger log = Logger.getLogger(IMediaApiDubbo.class);
	ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:conf/readerV5/applicationContext-im.xml");
	IMediaApi iMediaApi = (IMediaApi)factory.getBean("iMediaApi");
	String env = Config.getEnvironment().toString();
	String channelId;
	
	//channelType 1:已包月   2：未包月
	public String getMonthlyChannelId(String channelType) throws Exception{
		if(env.equals(TestEnvironment.TESTING.toString())){	
			String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
			if(channelType.equals("1")){
				List<String> list = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId);
				channelId = list.get(SqlUtil.getRandNum(list));
			}else if(channelType.equals("2")){
				channelId = ChannelDb.getMonthlyChannel(custId);
			}
			return channelId;
		}
		return null;
	}
	
	//mediaType: 1频道下的电子书    2频道下的纸书 即null 
	public String getMonthlyMediaId(String mediaType) throws Exception{
		if(env.equals(TestEnvironment.TESTING.toString())){	
			String mediaId="";
			if(mediaType.equals("1")){
				List<MediaBooklistDetail> list = MediaBookListDetailDb.getMediaIdList(channelId);
				for(int i=0; i<list.size(); i++){
					int type = Integer.valueOf(list.get(i).getType());
					if(type==1||type==2){
						mediaId = String.valueOf(list.get(i).getMediaId());
						break;
					}
				}
			}else{
				mediaId = "0";
			}
			return mediaId;		
		} 
		return null;
	}
	
	String flag="";
	public void setExpectedResult(String expected){
		flag = expected;
	}
	
	public String getChannelIdAndMediaId(String channelId, String mediaId) throws Exception{
		if(env.equals(TestEnvironment.TESTING.toString())){	
			Long _mediaId = Long.valueOf(mediaId);
			Long _channelId = Long.valueOf(channelId); 
			Long _returnChannelId = iMediaApi.getMeidaMonthlyInfo(paramMap.get("token"),_mediaId,_channelId);	
			return String.valueOf(_returnChannelId);
		}else 
			return null;
	}
	
	public boolean verify(String returnChannelId) throws Exception{	
		log.info("运行环境：" + env);
		if(env.equals(TestEnvironment.TESTING.toString())){	
			if(returnChannelId.equals(flag)){
				return true;
			}else
				return false;
		}
		return true;		
	}
	
	public static void main(String[] args){
		IMediaApiDubbo dubbo = new IMediaApiDubbo();
		try {
			String s = dubbo.getChannelIdAndMediaId("68","0");
			System.out.println("l: "+s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
