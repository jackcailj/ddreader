package com.dangdang.readerV5.channel;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelBgImgDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;

import com.dangdang.readerV5.reponse.GetBackgroundImgListResponse;
import com.dangdang.readerV5.reponse.UrlList;

/**
 * @author guohaiying
 */
public class GetBackgroundImgList extends FixtureBase{
	ReponseV2<GetBackgroundImgListResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBackgroundImgListResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<UrlList>  actual = ChannelBgImgDb.getBackImg(paramMap.get("type"));
            dataVerifyManager.add(new ListVerify(jsonResult.getData().getUrlList(),actual, true));           
        }
        super.dataVerify();
    }
}
