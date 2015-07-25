package com.dangdang.readerV5.comment;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.ddframework.util.Util;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.AddCommentResponse;

public class DelComment extends FixtureBase{
	String targetId = null;
	String commentId = null;
	public ReponseV2Base getResult(){
		return JSONObject.parseObject(result.toString(), ReponseV2Base.class);
	}
	
	/**
	 * 添加新评论 
	 */
	public String addComment(String targetId, String targetSource) throws Exception{		
		String param = "action=addComment&targetId="+targetId+"&content=评论评论"
				+ "&replyCommentId=0&replyId=0&commentParentId=0&targetSource="+targetSource+"&isAnonymous=0"
				+ "&token=&userName=whytest@dd.con&passWord=111111&loginType=email";
		Map<String,String> params = Util.generateMap(param);
		ParseResult parseResult=ParseParamUtil.parseParameter(params);
		params.putAll(Config.getCommonParam());
		// 添加评论
		String result = HttpDriver.doGet(Config.getUrl(), params,false);
		ReponseV2<AddCommentResponse> reponseResult = 
				JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AddCommentResponse>>(){});
		return Integer.toString(reponseResult.getData().getCommentId());
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
	   super.setParameters(params);		
	   if(params.get("targetId")!=null&&params.get("targetId").equalsIgnoreCase("FromDB")){
			String sql = "select target_id, comment_id from comment where is_delete=0 and status=1 "
					   + "and target_source="+params.get("targetSource")+" ORDER BY RAND() limit 1";
			Map<String, Object> map = DbUtil.selectOne(Config.BSAECOMMENT, sql);
			targetId = map.get("target_id").toString();
			params.put("targetId",targetId);
			commentId = addComment(targetId,params.get("targetSource"));
			params.put("commentId",commentId);
		}	
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2Base reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = "select * from comment where comment_id="+commentId;	
			Map<String,Object> comment = DbUtil.selectOne(Config.BSAECOMMENT, sql);
			dataVerifyManager.add(new ValueVerify<String>(comment.get("is_delete").toString(), "1", false));	
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
