package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.MediaRankingListResponse;

/**
 * 榜单接口
 * @author guohaiying
 *
 */
public class MediaRankingList extends FixtureBase{

	ReponseV2<MediaRankingListResponse> reponseResult;
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MediaRankingListResponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
		
			//验证json中返回的字段值
			log.info("验证json返回的字段值");
			//ChannelResponse dbResponse = ChannelSQL.getChannel(Integer.valueOf(paramMap.get("cId")));
			//dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
						
		}
		return dataVerifyManager.dataVerify();      	
	}
	
}
