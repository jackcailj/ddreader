package com.dangdang.readerV5.comment;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ContentList;
import com.dangdang.readerV5.reponse.ContentListData;

public class GetTagContentList extends FixtureBase {
	public ReponseV2<ContentListData> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ContentListData>>(){});
	}
	String sbuStr;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);	
		sbuStr = null;
		String sql = "SELECT * from tag_relation where target_source in (1000,4000,5000,6000) and recommend_status=1 order by rand() limit 3";
		if(paramMap.get("tagIds")!=null&&paramMap.get("tagIds").equalsIgnoreCase("One")){
			String tagId = DbUtil.selectList(Config.BSAECOMMENT, sql).get(0).get("tag_id").toString();	
			paramMap.put("tagIds",tagId);
			sbuStr = "(tag_id="+tagId+")";
		}
		if(paramMap.get("tagIds")!=null&&paramMap.get("tagIds").equalsIgnoreCase("Three")){
			List<TagRelation> list = DbUtil.selectList(Config.BSAECOMMENT, sql, TagRelation.class);
			String ids = list.get(0).getTagId()+","+list.get(1).getTagId()+","+list.get(2).getTagId();
			paramMap.put("tagIds",ids);
			sbuStr = "(tag_id="+list.get(0).getTagId()+" or tag_id="+list.get(1).getTagId()+" or tag_id="+list.get(2).getTagId()+")";
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<ContentListData> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			String sql = "SELECT * from tag_relation where "+sbuStr
					+ " and recommend_status=1  GROUP BY source_id ORDER BY target_source ASC";
			List<TagRelation> digest1 = DbUtil.selectList(Config.BSAECOMMENT, sql, TagRelation.class);
			ContentList content = reponseResult.getData().getContentList();
			int barSize = content.getBarList()!=null?content.getBarList().size():0;
			int channelSize = content.getChannelList()!=null? content.getChannelList().size():0;
			int digestSize = content.getDigestList()!=null?content.getDigestList().size():0;
			int mediaSize = content.getMediaList()!=null?content.getMediaList().size():0;
			int size = barSize + channelSize + digestSize + mediaSize;
			dataVerifyManager.add(new ValueVerify<Integer>(digest1.size(), size, false));
			super.dataVerify();			
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
