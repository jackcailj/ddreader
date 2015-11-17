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
import com.dangdang.readerV5.reponse.CommentList;

public class GetCommentList extends FixtureBase{
	String targetId = null;
	String commentId = null;
	public ReponseV2<CommentList> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CommentList>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);		
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("FromDB")){
			String sql = "select target_id, comment_id from comment where status=1 "
					   + "and target_source="+paramMap.get("targetSource")+" limit 10";
			Map<String, Object> map = DbUtil.selectList(Config.BSAECOMMENT, sql).get((new Random()).nextInt(10));
			targetId = map.get("target_id").toString();
			paramMap.put("targetId",targetId);
			if(paramMap.get("commentId")!=null&&paramMap.get("commentId").equalsIgnoreCase("FromDB")){
				commentId = map.get("comment_id").toString();
				paramMap.put("commentId",commentId);
			}
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<CommentList> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String subSql = (paramMap.get("commentId").equals("0"))?"":" and comment_id<"+commentId;
			String sql = "select * from comment where status=1 and target_source="+paramMap.get("targetSource")
					   +" and target_id="+targetId+" and comment_parent_id=0"+subSql+" order by last_modified_date DESC";
			List<Comment> comment = DbUtil.selectList(Config.BSAECOMMENT, sql, Comment.class);
		    int comCount = comment.size();
			for(int i=0; i<comment.size(); i++){
				//把被删除的评论过滤掉
				if(comment.get(i).getIsDelete()==1){
					comCount--;
					continue;
				}
				//验证一级评论及每个评论下显示的回复的大小
				Map<String,Object> map1 = new HashMap<String,Object>();
				Map<String,Object> map2 = new HashMap<String,Object>();					
				sql = "select * from comment where status=1 "
						   + "and target_source="+paramMap.get("targetSource")+" and target_id="+targetId
						   +" and comment_parent_id="+comment.get(i).getCommentId();
				List<Comment> subComment = DbUtil.selectList(Config.BSAECOMMENT, sql, Comment.class);			
				int subComCount = subComment.size();
				//验证回复
				for(int j=0; j<subComment.size(); j++){
					//把被删除的回复过滤掉
					if(subComment.get(j).getIsDelete()==1){
						subComCount--;
						continue;
					}
					Map<String,Object> map3 = new HashMap<String,Object>();
					Map<String,Object> map4 = new HashMap<String,Object>();	
					map3.put("commentId", subComment.get(j).getCommentId());
					map3.put("targetId", subComment.get(j).getTargetId());
					map4.put("commentId", reponseResult.getData().getCommentList().get(i).get(j+1).getCommentId());
					map4.put("targetId", reponseResult.getData().getCommentList().get(i).get(j+1).getTargetId());
					dataVerifyManager.add(new ValueVerify(map3, map4, true));
				}
				//验证一级评论
				int size = subComCount<10?subComCount:9;				
				map1.put("commentId", comment.get(i).getCommentId());
				map1.put("targetId", comment.get(i).getTargetId());
				map1.put("size", size);
				map2.put("commentId", reponseResult.getData().getCommentList().get(i).get(0).getCommentId());
				map2.put("targetId", reponseResult.getData().getCommentList().get(i).get(0).getTargetId());
				map2.put("size", reponseResult.getData().getCommentList().get(i).size()-1);
				dataVerifyManager.add(new ValueVerify(map1, map2, true));
			}		
			 
			if(comCount<20){
				dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getCommentList().size(),comCount,false));	
			}
			else{
				//默认一页显示20条一级评论
				dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getCommentList().size(),20,false));	
			}
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
