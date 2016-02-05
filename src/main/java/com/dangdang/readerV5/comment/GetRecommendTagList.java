package com.dangdang.readerV5.comment;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.TagInfoVoList;
import com.dangdang.BaseComment.meta.TagInfo;

public class GetRecommendTagList extends FixtureBase {
	public ReponseV2<TagInfoVoList> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<TagInfoVoList>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<TagInfoVoList> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = "SELECT * FROM `tag_info` where is_recommend=1";	
			List<TagInfo> tagList = DbUtil.selectList(Config.BSAECOMMENT, sql, TagInfo.class);
			dataVerifyManager.add(new ValueVerify<Integer>(tagList.size(), reponseResult.getData().getTagInfoVoList().size(),false));			
			dataVerifyManager.add(new ListVerify(reponseResult.getData().getTagInfoVoList(),tagList, true));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
