package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaBooklist;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaBooklistDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.readerV5.reponse.ChannelBookList;
import com.dangdang.readerV5.reponse.ChannelBookListResponse;

/**
 * 书单基本信息接口
 * @author guohaiying
 */
public class BookList extends FixtureBase{
	ReponseV2<ChannelBookListResponse> jsonResult;	
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelBookListResponse >>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){  
        	String token = paramMap.get("token");
            ChannelBookList actualBookList = jsonResult.getData().getBookList();
        	MediaBooklist expectedBookList = MediaBooklistDb.getBookListMsg(actualBookList.getChannelId());
        	//验证name description imageUrl bookNum isMine
        	dataVerifyManager.add(new ValueVerify<String>(actualBookList.getName(),expectedBookList.getName()).setVerifyContent("验证name"));
        	dataVerifyManager.add(new ValueVerify<String>(actualBookList.getDescription(),expectedBookList.getDescription()).setVerifyContent("验证description"));
        	dataVerifyManager.add(new ValueVerify<String>(actualBookList.getImageUrl(),expectedBookList.getImageUrl()).setVerifyContent("验证imageUrl"));
        	dataVerifyManager.add(new ValueVerify<Integer>(actualBookList.getBookNum(),expectedBookList.getBookNum()).setVerifyContent("验证bookNum"));      	
        	
           	String custId = UserDeviceDb.getCustIdByToken(token);
           	String actualMonthlyStatus = jsonResult.getData().getHasBoughtMonthly();
        	if(custId!=null){
        		custId = UserDeviceDb.getCustIdByToken(token);       		
        		//验证hasBoughtMonthly
        		String expentedMonthlyStatus = MediaMonthlyAuthorityDb.getUserMonthlyAuthority(custId, actualBookList.getChannelId());
        		dataVerifyManager.add(new ValueVerify<String>(actualMonthlyStatus, expentedMonthlyStatus).setVerifyContent("验证是否有包月权限"));
        		
        		//验证是否是自己的频道 isMine
        		String expectedChannelId = ChannelDb.getChannelIdByCustId(custId);
        		if(actualBookList.getChannelId().equals(expectedChannelId)){
        			dataVerifyManager.add(new ValueVerify<String>(actualBookList.getIsMine(), "1").setVerifyContent("验证是否是自己的频道"));
        		}else
        			dataVerifyManager.add(new ValueVerify<String>(actualBookList.getIsMine(), "0").setVerifyContent("验证是否是自己的频道"));
        	}else{
        		dataVerifyManager.add(new ValueVerify<String>(actualMonthlyStatus, "0").setVerifyContent("验证是否有包月权限"));
        		dataVerifyManager.add(new ValueVerify<String>(actualBookList.getIsMine(), "0").setVerifyContent("验证是否是自己的频道"));
        	}
        	
        	//验证书单书数量
        	//dataVerifyManager.add(new ValueVerify<Integer>(actualBookList.getBookNum(),MediaBookListDetailDb.getMediaIdList(actualBookList.getChannelId().toString()).size()).setVerifyContent("验证书单书数量"));
        }
        super.dataVerify();
    }
	
}
