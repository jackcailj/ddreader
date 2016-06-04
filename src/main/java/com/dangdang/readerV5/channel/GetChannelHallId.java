package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaBooklistDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetChannelHallIdResponse;

/**
 * @author guohaiying
 * @date 2016年4月15日 下午5:05:52
 */
public class GetChannelHallId extends FixtureBase{

	ReponseV2<GetChannelHallIdResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetChannelHallIdResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){   
        	String vipId = jsonResult.getData().getChannelHallId();
        	String bookListId = MediaBooklistDb.getBookListMsg(vipId).getBooklistId().toString();
        	dataVerifyManager.add(new ValueVerify<Object>(bookListId, null).setVerifyContent("验证VIP频道书单"), VerifyResult.FAILED);
        	dataVerifyManager.add(new ValueVerify<Object>(MediaBookListDetailDb.getCount(bookListId), null).setVerifyContent("验证VIP频道书单书数量"), VerifyResult.FAILED);
        }
        super.dataVerify();
    }
}
