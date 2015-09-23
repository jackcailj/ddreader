package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

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
		return super.doGet(exceptedCode);
	}
	
	public boolean doGets(String exceptedCode) throws Exception {	
		boolean flag = true;
		String cName = paramMap.get("cId");
		System.out.println("aaa"+cName);
		List<String>  cIds = new ArrayList<String>();
		cIds = ChannelSubSQL.getChannelId(cName);
		for(int i=0; i<cIds.size(); i++){
			paramMap.put("cId", cIds.get(i));
			if(!doGet(exceptedCode))
				flag = false;
			Thread.sleep(1000);
		}
		return flag;
	}
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelsubReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证订阅/取消订阅是否成功");	
			String sub = ChannelSubSQL.isSub(login.getCustId(),paramMap.get("cId"),paramMap.get("op"));
			dataVerifyManager.add(new ValueVerify<String>("1", sub).setVerifyContent("验证订阅/取消订阅是否成功"));
		
			//验证订阅总数
			log.info("验证订阅总数");	
			String dbTotal = ChannelSubSQL.getSubTotal(paramMap.get("cId"));
			String actual = reponseResult.getData().getSubNumber();
			dataVerifyManager.add(new ValueVerify<String>(actual, dbTotal).setVerifyContent("验证频道订阅总数"));	
		}
		return dataVerifyManager.dataVerify();    
	 }
}
