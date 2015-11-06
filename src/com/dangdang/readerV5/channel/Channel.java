package com.dangdang.readerV5.channel;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.readerV5.reponse.ChannelResponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 
 * @author guohaiying
 *
 */
public class Channel extends FixtureBase{	
	ReponseV2<ChannelResponse> jsonResult;
	String flag = "";
	public Channel(){
		addAction("channel");
	}
	
	@SystemUnderTest
	public ChannelDb db = new ChannelDb();
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception{
		flag = params.get("cId");
		super.setParameters(params);
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelResponse >>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String channelId = paramMap.get("cId");
        	com.dangdang.digital.meta.Channel dbChannel = ChannelDb.getChannel(channelId);
        	com.dangdang.readerV5.reponse.Channel jsonChannel = jsonResult.getData().getChannel();
        	//验证频道基本信息   除hasArtical,hasBoughtMonthly,isMine,isSub
        	dataVerifyManager.add(new ValueVerify<Long>(jsonChannel.getChannelId(), dbChannel.getChannelId()).setVerifyContent("验证ChannelId"));
        	dataVerifyManager.add(new ValueVerify<String>(jsonChannel.getDescription(), dbChannel.getDescription()).setVerifyContent("验证Description"));
        	dataVerifyManager.add(new ValueVerify<String>(jsonChannel.getIcon(), dbChannel.getIcon()).setVerifyContent("验证Icon"));
        	dataVerifyManager.add(new ValueVerify<Integer>(jsonChannel.getIsAllowMonthly(), dbChannel.getIsAllowMonthly()).setVerifyContent("验证IsAllowMonthly"));
        	dataVerifyManager.add(new ValueVerify<Integer>(jsonChannel.getMonthlyType(), dbChannel.getMonthlyType()).setVerifyContent("验证MonthlyType"));
            //owner
        	//ownerType
        	dataVerifyManager.add(new ValueVerify<Integer>(jsonChannel.getShelfStatus(), dbChannel.getShelfStatus()).setVerifyContent("验证ShelfStatus"));
        	
        	dataVerifyManager.add(new ExpressionVerify(Math.abs(jsonChannel.getSubNumber()-dbChannel.getSubNumber())<10?true:false).setVerifyContent("验证SubNumber"));
        	//tagIds
        	//tagNames
        	dataVerifyManager.add(new ValueVerify<String>(jsonChannel.getTitle(), dbChannel.getTitle()).setVerifyContent("验证Title"));
        
        	//if() hasArtical 验证频道有无文章
        	//if() hasBoughtMonthly 验证是否有包月权限
        	//if() isSub  验证是否已经订阅
        	//if(flag.contain("")) isMine  验证我的频道标识
        
        }
        super.dataVerify();
    }
	
}
