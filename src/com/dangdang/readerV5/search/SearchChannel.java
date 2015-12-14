package com.dangdang.readerV5.search;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.ChannelOwnerDb;
import com.dangdang.db.ucenter.LoginRecordDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.ChannelOwner;
import com.dangdang.readerV5.reponse.SearchChannelList;
import com.dangdang.readerV5.reponse.SearchChannelResponse;
import com.dangdang.ucenter.meta.LoginRecord;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchChannel extends FixtureBase{
	ReponseV2<SearchChannelResponse> jsonResult;
	
	@Override
	public void doWork() throws Exception{
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SearchChannelResponse>>(){});
	}
	
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<SearchChannelList> actualSearch = jsonResult.getData().getSearchChannelList();
        	for(int i=0; i<actualSearch.size(); i++){
        		String actualChannelId = actualSearch.get(i).getChannelId();
        		String custId = actualSearch.get(i).getCustId();
        		Channel expectedChannel = ChannelDb.getChannel(actualChannelId);
        		dataVerifyManager.add(new ValueVerify<Integer>(1,expectedChannel.getShelfStatus()).setVerifyContent("验证频道["+actualChannelId+"]是否上架"));
        		dataVerifyManager.add(new ValueVerify<String>(custId,String.valueOf(expectedChannel.getCustId())).setVerifyContent("验证频道custId"));
        		dataVerifyManager.add(new ValueVerify<String>(actualSearch.get(i).getChannelPic(),expectedChannel.getIcon()).setVerifyContent("验证频道Icon"));
        		dataVerifyManager.add(new ValueVerify<String>(actualSearch.get(i).getDescription(),expectedChannel.getDescription()).setVerifyContent("验证频道Description"));
        		dataVerifyManager.add(new ValueVerify<String>(actualSearch.get(i).getTitle(),expectedChannel.getTitle()).setVerifyContent("验证频道Title"));
        		//dataVerifyManager.add(new ValueVerify<Integer>(actualSearch.get(i).getSubscriptions(),expectedChannel.getSubNumber()).setVerifyContent("验证频道SubNumber"));
        		//验证昵称
        		ChannelOwner owner = ChannelOwnerDb.getChannelOwner(custId);
        		if(owner==null)
        			dataVerifyManager.add(new ValueVerify<String>(actualSearch.get(i).getStatus(), "0").setVerifyContent("验证频道认证status"));
        		else
        			dataVerifyManager.add(new ValueVerify<String>(actualSearch.get(i).getStatus(), String.valueOf(owner.getStatus())).setVerifyContent("验证频道认证status"));
        		LoginRecord loginRecord = LoginRecordDb.getLoginRecord(custId);
        		dataVerifyManager.add(new ExpressionVerify(loginRecord.getCustNickname().contains(actualSearch.get(i).getChannelNickName())).setVerifyContent("验证频道NickName"));
        		
        	}         
        }
        super.dataVerify();
    }
}
