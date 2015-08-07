package com.dangdang.readerV5.channel;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.channel.ChannelColumnSQL;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;
import com.dangdang.readerV5.reponse.ChannelList;

import fitnesse.slim.SystemUnderTest;

/**
 * 频道列表接口
 * @author guohaiying
 *
 */
public class ChannelColumn extends FixtureBase{

	ReponseV2<ChannelColumnReponse> reponseResult;
		
	@SystemUnderTest
	public ChannelColumnSQL sql = new ChannelColumnSQL();

	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelColumnReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证频道栏目下的频道列表");	
			int num = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start"));
			String custId;
			if(super.login==null){
				custId = null;
			}else{
				custId=getCustId();
			}
			ChannelColumnReponse dbResponse = ChannelColumnSQL.getChannelColumn(paramMap.get("columnType"),custId, num);
			dataVerifyManager.add(new ValueVerify<ChannelColumnReponse>(reponseResult.getData(), dbResponse, true));
			
			//验证返回的订阅数是否正确
			log.info("验证订阅数");
			for(int i=0; i<reponseResult.getData().getChannelList().size(); i++){
				String actual = reponseResult.getData().getChannelList().get(i).getSubNumber();
				String expected = ChannelColumnSQL.getChannelSub(reponseResult.getData().getChannelList().get(i).getChannelId());
				log.info("json中订阅数: " +actual);
			//	log.info("预期订阅数: " +expected);
				dataVerifyManager.add(new ValueVerify<String>(actual, expected));							
			}
			
			}
			return dataVerifyManager.dataVerify();      	
		}
	public boolean verifyChannel(String channelId){
		boolean flag = true;		
		List<ChannelList> channelList = reponseResult.getData().getChannelList();
		for(int i=0; i<channelList.size(); i++){
			if(channelList.get(i).getChannelId().equals(channelId)){
				flag = false;
				break;
			}
		}
		return flag;
	}
}
