package com.dangdang.readerV5.comment;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.AddCommentResponse;

public class AddComment extends FixtureBase{
	 
	public ReponseV2<AddCommentResponse> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<AddCommentResponse>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);		
		if(paramMap.get("targetId")!=null&&paramMap.get("targetId").equalsIgnoreCase("FromDB")){
			//类型： 1:翻篇儿; 2:抢先读; 3:频道; 4:贴子; 5:攻略
			//来源：1000:书吧 2000：翻篇 3000：抢先读 4000：频道_(必填) 7000 攻略_
			String source = paramMap.get("targetSource");
			String type = null;
//			switch(source){
//			case "1000":type="4";
//				        break;
//			case "2000":type="1";
//				        break;
//			case "3000":type="2";
//				        break;
//			case "4000":type="3";
//			            break;
//			case "7000":type="5";
//			            break;
//			}				
			String targetId = MediaDigestDb.getTargetId(type).get((new Random()).nextInt(10)).getId().toString();;
			paramMap.put("targetId",targetId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<AddCommentResponse> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			int commentId = reponseResult.getData().getCommentId();
			String sql = "select * from comment where comment_id="+commentId;	
			List<Map<String,Object>> list = DbUtil.selectList(Config.BSAECOMMENT, sql);
			dataVerifyManager.add(new ValueVerify<Integer>(list.size(), 1,false));			
			dataVerifyManager.add(new ValueVerify<String>(list.get(0).get("content").toString(), 
					                                      paramMap.get("content"),false));
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
