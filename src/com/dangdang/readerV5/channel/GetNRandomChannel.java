package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaColumnContentDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaColumnContent;

import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.GetNRandomChannelResponse;

/**
 * @author guohaiying
 */
public class GetNRandomChannel extends FixtureBase{
	ReponseV2<GetNRandomChannelResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetNRandomChannelResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<ChannelList> actualChannelList = jsonResult.getData().getChannelList();
        	List<String> actualChannelIds = new ArrayList<String>();
        	List<String> expectedChannelIds = new ArrayList<String>();
        	for(int i=0; i<actualChannelList.size(); i++){
        		System.out.println("json: "+ actualChannelList.get(i).getChannelId());
        		actualChannelIds.add(actualChannelList.get(i).getChannelId());
        	}
        	
        	List<MediaColumnContent> expectedChannelList = MediaColumnContentDb.getChannelList(paramMap.get("columnType"));
        	for(int i=0;i<expectedChannelList.size(); i++){
        		System.out.println("db: "+ expectedChannelList.get(i).getSaleId());
        		expectedChannelIds.add(String.valueOf(expectedChannelList.get(i).getSaleId()));
        	}
        	
            dataVerifyManager.add(new ExpressionVerify(expectedChannelIds.containsAll(actualChannelIds)).setVerifyContent("验证channelList"));           
            //验证返回的数量
            int num=0;
            try{
            	num = Integer.valueOf(paramMap.get("num"));
            }catch(Exception e){
            	num=5;
            }
            dataVerifyManager.add(new ValueVerify<Integer>(actualChannelList.size(),num).setVerifyContent("验证List size"));
        
        }
        super.dataVerify();
    }
}
