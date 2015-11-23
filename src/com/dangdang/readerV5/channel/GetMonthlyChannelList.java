package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.digital.meta.Channel;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetMonthlyChannelListResponse;
import com.dangdang.readerV5.reponse.MonthlyChannelList;

/**
 * @author guohaiying
 */
public class GetMonthlyChannelList extends FixtureBase{
	ReponseV2<GetMonthlyChannelListResponse> jsonResult;

    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMonthlyChannelListResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
        	List<String> expectedUserMonthlyChannel = MediaMonthlyAuthorityDb.getUserAllMonthlyChannelID(custId);       	
        	List<MonthlyChannelList> actualList = jsonResult.getData().getChannelList();
        	List<String> actualUserChannels = new ArrayList<String>();
        	for(int i=0; i<actualList.size(); i++){    
        		System.out.println("bbbbbb: "+ actualList.get(i).getChannelId());
        		actualUserChannels.add(actualList.get(i).getChannelId());
        	}
        	dataVerifyManager.add(new ExpressionVerify(expectedUserMonthlyChannel.containsAll(actualUserChannels)).setVerifyContent("验证是否是已包月的频道"));
        	
        	// 验证title description icon 
        	for(int i=0; i<actualUserChannels.size(); i++){
        		Channel expectedChannel = ChannelDb.getChannel(actualUserChannels.get(i));
	        	dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getTitle(), expectedChannel.getTitle()).setVerifyContent("验证title"));
        		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDescription(), expectedChannel.getDescription()).setVerifyContent("验证description"));
        		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getIcon(), expectedChannel.getIcon()).setVerifyContent("验证icon"));      		
        	}	
        }
        super.dataVerify();
    }
}
