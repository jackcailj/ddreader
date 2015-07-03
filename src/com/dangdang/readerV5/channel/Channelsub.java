package com.dangdang.readerV5.channel;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ChannelSQL;
import com.dangdang.readerV5.reponse.ChannelsubReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 频道订阅接口
 * @author guohaiying
 *
 */
public class Channelsub extends FixtureBase{
	
	public Logger log = Logger.getLogger(Channelsub.class);
	ReponseV2<ChannelsubReponse> reponseResult;	 
	
	@SystemUnderTest
	public ChannelSQL sql = new ChannelSQL();	
    
	public boolean verifyResult() throws Exception{
		 //首次订阅，channel_sub_user中增加一条用户记录，type=1(发送请求前查询一次，返回结果为0)
		 //取消订阅，或再次订阅， 注意判断表中字段type值(发送请求前查询一次，返回结果为1)
		 //SELECT * FROM `channel_sub_user` WHERE channel_id=30 AND cust_id=50098052 AND type=1;
		 //订阅总数的验证，SELECT COUNT(*) FROM `channel_sub_user` WHERE channel_id=30 AND type=1;
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelsubReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证订阅/取消订阅是否成功");	
			int custID = Integer.valueOf(login.getCustId());
			int db = ChannelSQL.getUserChannelSub(custID, Integer.valueOf(paramMap.get("cId")));
			int op = Integer.valueOf(paramMap.get("op"));//订阅：1 取消订阅：0
			if(op==1){
				dataVerifyManager.add(new ValueVerify<Integer>(1, db));
			}else{
				dataVerifyManager.add(new ValueVerify<Integer>(0, db));
			}
			
			//验证订阅总数
			log.info("验证订阅总数");	
			int dbTotal = ChannelSQL.getChannelSub(Integer.valueOf(paramMap.get("cId")));
			int actual = reponseResult.getData().getSubNumber();
			dataVerifyManager.add(new ValueVerify<Integer>(actual, dbTotal));
			
		}
		return dataVerifyManager.dataVerify();    
	 }
}
