package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelSubUserDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.HotChannelResponse;

/**
 * @author guohaiying
 */
public class HotChannel extends FixtureBase{
	ReponseV2<HotChannelResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<HotChannelResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
    	if(reponseV2Base.getStatus().getCode()==0){
    		List<String> actualChannelIds = new ArrayList<String>();
    		List<ChannelList> actualChannelList = jsonResult.getData().getChannelList();
    		for(int i=0; i<actualChannelList.size(); i++){
    			System.out.println("jsonList: "+actualChannelList.get(i).getChannelId());
    			actualChannelIds.add(actualChannelList.get(i).getChannelId());
    		}
    		List<String> expectedChannelIds = ChannelSubUserDb.getHotChannelIds();        	
    		dataVerifyManager.add(new ExpressionVerify(expectedChannelIds.containsAll(actualChannelIds)).setVerifyContent("验证热门频道"));           
        }
        super.dataVerify();
    }
}
