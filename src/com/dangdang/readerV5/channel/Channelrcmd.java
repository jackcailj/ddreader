package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Channel;

import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.ChannelrcmdReponse;

/**
 * @author guohaiying
 */
public class Channelrcmd extends FixtureBase{
	ReponseV2<ChannelrcmdReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelrcmdReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
    	if(reponseV2Base.getStatus().getCode()==0){
    		List<String> actualChannelIds = new ArrayList<String>();
    		List<String> expectedChannelIds = new ArrayList<String>();
    		List<ChannelList> actualChannelList = jsonResult.getData().getChannelList();
    		for(int i=0;i<actualChannelList.size();i++){
    			System.out.println("json: "+ actualChannelList.get(i).getChannelId());
    			actualChannelIds.add(actualChannelList.get(i).getChannelId());
    		}
    		List<Channel> expectedChannelList = ChannelDb.getRelatedChannel(paramMap.get("cId"), 30);
    		for(int i=0;i<expectedChannelList.size(); i++){
    			System.out.println("db: "+ expectedChannelList.get(i).getChannelId());
    			expectedChannelIds.add(String.valueOf(expectedChannelList.get(i).getChannelId()));
    		}
  	
    		dataVerifyManager.add(new ExpressionVerify(expectedChannelIds.containsAll(actualChannelIds)).setVerifyContent("验证相关频道"));           
        }
        super.dataVerify();
    }
}
