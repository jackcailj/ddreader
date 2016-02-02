package com.dangdang.readerV5.search;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.SearchMediaResponse;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchMedia extends FixtureBase {
	ReponseV2<SearchMediaResponse> jsonResult;

	@Override
	public void doWork() throws Exception{
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SearchMediaResponse>>(){});
	}
	
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	
        }
    }
}
