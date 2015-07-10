package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ChannelSQL;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;
import com.dangdang.readerV5.reponse.ColumnReponse;
import fitnesse.slim.SystemUnderTest;

/**
 * 频道列表接口
 * @author guohaiying
 *
 */
public class ChannelColumn extends FixtureBase{

	ReponseV2<ChannelColumnReponse> reponseResult;
		
	@SystemUnderTest
	public ChannelSQL sql = new ChannelSQL();

	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelColumnReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证频道栏目下的频道列表");	
			ChannelColumnReponse dbResponse = ChannelSQL.getChannelColumn(paramMap.get("columnType"));
			dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
			
			//验证返回的订阅数是否正确
			log.info("验证订阅数");
			for(int i=0; i<reponseResult.getData().getChannelList().size(); i++){
				Integer actual = reponseResult.getData().getChannelList().get(i).getSubNumber();
				Integer expected = ChannelSQL.getChannelSub(reponseResult.getData().getChannelList().get(i).getChannelId());
				log.info("json中订阅数: " +actual);
				log.info("预期订阅数: " +expected);
				dataVerifyManager.add(new ValueVerify<Integer>(actual, expected));							
			}
			
			}
			return dataVerifyManager.dataVerify();      	
		}
}
