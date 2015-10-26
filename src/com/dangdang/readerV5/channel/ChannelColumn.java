package com.dangdang.readerV5.channel;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.channel.ChannelColumnSQL;
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
			log.info("验证频道栏目"+paramMap.get("columnType")+"下的频道列表: ");	
			int num = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start"))+1;
			String custId;
			if(super.login==null){
				custId = null;
			}else{
				custId=getCustId();
			}
			ChannelColumnReponse dbResponse = ChannelColumnSQL.getChannelColumn(paramMap.get("columnType"),custId, num);
			dataVerifyManager.add(new ListVerify(dbResponse.getChannelList(), reponseResult.getData().getChannelList(), true).setVerifyContent("验证ChannelList"));
			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getColumnCode(), reponseResult.getData().getColumnCode()).setVerifyContent("验证ColumnCode"));
			dataVerifyManager.add(new ValueVerify<Integer>(dbResponse.getCount(), reponseResult.getData().getCount()).setVerifyContent("验证Count"));
			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getName(), reponseResult.getData().getName()).setVerifyContent("验证Name"));
			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getTips(), reponseResult.getData().getTips()).setVerifyContent("验证Tips"));
			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getTotal(), reponseResult.getData().getTotal()).setVerifyContent("验证Total"));
			return dataVerifyManager.dataVerify();    
			
		}else
			return true;
			  	
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
