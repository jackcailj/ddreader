package com.dangdang.readerV5.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.Comment;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.CommentReplyList;

public class GetCommentReplyList  extends FixtureBase{
	String targetId = null;
	String commentId = null;
	int count = 0;
	public ReponseV2<CommentReplyList> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CommentReplyList>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);		
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("FromDB")){
			String sql = "select target_id, comment_parent_id, count(*), target_source "
					+ "from comment where "+((Config.getEnvironment()== TestEnvironment.TESTING)?"is_delete=0 and ":"")+
					"status=1 and target_source="+paramMap.get("targetSource")+""
					+ " and comment_parent_id!=0 GROUP BY comment_parent_id having(count(comment_parent_id)>9)";
			List<Map<String, Object>> list = DbUtil.selectList(Config.BSAECOMMENT, sql);
			Map<String, Object> map =list.get((new Random()).nextInt(list.size()));
			targetId = map.get("target_id").toString();
			paramMap.put("targetId",targetId);
			count = Integer.parseInt(map.get("count(*)").toString());
			if(paramMap.get("commentId")!=null&&paramMap.get("commentId").equalsIgnoreCase("FromDB")){
				commentId = map.get("comment_parent_id").toString();
				paramMap.put("commentId",commentId);
			}
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<CommentReplyList> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){			
			String sql = "select * from comment where status=1 "
					   + "and target_source="+paramMap.get("targetSource")+" and target_id="+targetId
					   +" and comment_parent_id="+commentId;
			List<Comment> comment = DbUtil.selectList(Config.BSAECOMMENT, sql, Comment.class);
			int size = comment.size();						
			//验证回复
			for(int i=0,j=9; j<comment.size(); j++,i++){
				if(comment.get(j).getIsDelete()==1){
					size--;
					i--;
					continue;
				}
				Map<String,Object> map1 = new HashMap<String,Object>();
				Map<String,Object> map2 = new HashMap<String,Object>();	
				map1.put("commentId", comment.get(j).getCommentId());
				map1.put("targetId", comment.get(j).getTargetId());
				map2.put("commentId", reponseResult.getData().getCommentReplyList().get(i).getCommentId());
				map2.put("targetId", reponseResult.getData().getCommentReplyList().get(i).getTargetId());
				dataVerifyManager.add(new ValueVerify(map1, map2, true));
			}
			dataVerifyManager.add(new ValueVerify<Integer>(size-9<0?0:size-9, reponseResult.getData().getCommentReplyList().size(), false));

			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
