package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaListCategoryDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaListCategory;
import com.dangdang.readerV5.reponse.MediaRankingListResponse;

/**
 * 榜单接口
 * @author guohaiying
 * @date 2016年5月24日 下午3:04:50
 */
public class MediaRankingList extends FixtureBase{
	ReponseV2<MediaRankingListResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MediaRankingListResponse>>(){});
    }
    
	//验证结果
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String categoryCode = paramMap.get("channelType") + "_" + paramMap.get("listType");
        	MediaListCategory mc = MediaListCategoryDb.get(categoryCode);
        	dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getName(), mc.getCategoryName()).setVerifyContent("验证name!"));
        	dataVerifyManager.add(new ExpressionVerify(jsonResult.getData().getCount()>0).setVerifyContent("验证count>0!"));
        	dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getListType(), mc.getCategoryCode()).setVerifyContent("验证listType!"));
        	dataVerifyManager.add(new ExpressionVerify(jsonResult.getData().getSaleList().size()>0).setVerifyContent("验证saleList不为空!"));
        }
        super.dataVerify();       	
	}
	
}
