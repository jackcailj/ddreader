package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.channel.ChannelSubSQL;
import com.dangdang.readerV5.reponse.ChannelsubReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 频道订阅接口
 * @author guohaiying
 *
 */
public class ChannelSub extends FixtureBase{
	String subTotal;
	ReponseV2<ChannelsubReponse> reponseResult;	

	@SystemUnderTest
	public ChannelSubSQL sql = new ChannelSubSQL();	
	
	@Override
	public boolean doGet(String exceptedCode) throws Exception {		
		//subTotal= ChannelSubSQL.getSubTotal(paramMap.get("cId"));
		return super.doGet(exceptedCode);
	}
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelsubReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证订阅/取消订阅是否成功");	
			String sub = ChannelSubSQL.isSub(login.getCustId(),paramMap.get("cId"),paramMap.get("op"));
			dataVerifyManager.add(new ValueVerify<String>("1", sub));
		
			//验证订阅总数
			log.info("验证订阅总数");	
			String dbTotal = ChannelSubSQL.getSubTotal(paramMap.get("cId"));
			String actual = reponseResult.getData().getSubNumber();
			dataVerifyManager.add(new ValueVerify<String>(actual, dbTotal));
			//dataVerifyManager.add(new ValueVerify<String>(dbTotal, String.valueOf(Integer.valueOf(subTotal)+1)));
			
		}
		return dataVerifyManager.dataVerify();    
	 }
}
