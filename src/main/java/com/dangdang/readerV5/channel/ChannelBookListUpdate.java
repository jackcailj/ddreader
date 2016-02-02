package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaBooklistDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaBooklist;
import com.dangdang.readerV5.reponse.ChannelBookListUpdateResponse;

/**
 * @author guohaiying
 */
public class ChannelBookListUpdate extends FixtureBase{
	ReponseV2<ChannelBookListUpdateResponse> jsonResult;	
		
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelBookListUpdateResponse >>(){});
	}

	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){
			String type = paramMap.get("type");
	      	String cbid = paramMap.get("cbid");
	      	String icon = paramMap.get("icon");
	      	String description = paramMap.get("description");
	      	String name = paramMap.get("name");       	
        	
        	//验证个人频道相关修改
        	if(type.equals("0")){
        		com.dangdang.digital.meta.Channel expectedChannel = ChannelDb.getChannel(cbid);
        		//验证icon title description
        		if(!icon.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(icon, expectedChannel.getIcon()).setVerifyContent("验证Icon"));
        		if(!name.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(name, expectedChannel.getTitle()).setVerifyContent("验证Title"));      	
        		if(!description.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(description, expectedChannel.getDescription()).setVerifyContent("验证Description"));	      	
        	}else{
        		//验证个人书单相关修改
        		MediaBooklist expectedBookList = MediaBooklistDb.getBookListMsg2(cbid);
           		if(!icon.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(icon, expectedBookList.getImageUrl()).setVerifyContent("验证Icon"));
        		if(!name.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(name, expectedBookList.getName()).setVerifyContent("验证Title"));      	
        		if(!description.equals(""))
        			dataVerifyManager.add(new ValueVerify<String>(description, expectedBookList.getDescription()).setVerifyContent("验证Description"));	      	
        	}   
		}
		super.dataVerify();
	}
}
