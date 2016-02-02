package com.dangdang.readerV5.comment;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.CommentCount;

public class GetTargetCommentCount extends FixtureBase{
	String targetId = null;
	public ReponseV2<CommentCount> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CommentCount>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);		
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("FromDB")){
			String sql = "select target_id from comment where "+((Config.getEnvironment()== TestEnvironment.TESTING)?"is_delete=0 and ":"")+"status=1 "
					   + "and target_source="+paramMap.get("targetSource")+" limit 1";
			targetId = DbUtil.selectOne(Config.BSAECOMMENT, sql).get("target_id").toString();	
			paramMap.put("targetId",targetId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<CommentCount> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = "select * from comment_target_count where target_id="+targetId+
					" and target_source="+paramMap.get("targetSource");	
			CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, sql, CommentTargetCount.class);
			dataVerifyManager.add(new ValueVerify<Integer>(count.getCommentCount(), reponseResult.getData().getCommentCount(),false));			
			dataVerifyManager.add(new ValueVerify<Integer>(count.getPraiseCount(), reponseResult.getData().getUpcommentCount(),false));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
