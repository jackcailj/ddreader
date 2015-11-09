package com.dangdang.readerV5.comment;

import java.util.List;
import java.util.Map;

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
//			String sql = "select target_id from comment where is_delete=0 and status=1 "
//					   + "and target_source="+paramMap.get("targetSource")+" limit 1";
//			sql = "select * ";
			//类型： 1:翻篇儿; 2:抢先读; 3:频道; 4:贴子; 5:攻略
			//来源：1000:书吧 2000：翻篇 3000：抢先读 4000：频道_(必填) 7000 攻略_
			String source = paramMap.get("targetSource");
			String type = null;
			switch(source){
			case "1000":type="4";
				        break;
			case "2000":type="1";
				        break;
			case "3000":type="2";
				        break;
			case "4000":type="3";
			            break;
			case "7000":type="5";
			            break;
			}				
			String targetId = MediaDigestDb.getNewDigest(type,"0").get(0).getId().toString();;
			
			//String targetId = DbUtil.selectOne(Config.BSAECOMMENT, sql).get("target_id").toString();	
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
	
	//TODO 查找target_id的sql语句，需要进一步处理，有时能找到已被删除的帖子的id
	
//	select * from article where media_digest_id=1117
//
//			select * from article where media_digest_id=999
//			select * from article where media_digest_id=1399
//	replyId为字母字，返回错误码为200
//	http://10.255.223.117/media/api2.go?deviceType=Android&targetId=1399&replyCommentId=750&commentParentId=749&clientOs=5.0.0&resolution=1280*720&content=%E8%AF%84%E8%AE%BA%E5%9B%9E%E5%A4%8D&targetSource=1000&token=3f2c0af74e17f47037a9701df1742cb6&clientVersionNo=5.0.0&activityId=0&isAnonymous=0&deviceSerialNo=863151026834264&replyId=UyFgrQr%3Ca+title%3D%22create+page%22+href%3D%22FrontPage.InterfaceTestPage.TestScript.ReviewSuite.UyFgrQr%3Fedit%26nonExistent%3Dtrue%22%3E%5B%3F%5D%3C%2Fa%3E%25252Bjcxms6CjJdqf7w%25253D%25253D&serverVersionNo=1.2.1&action=addComment&permanentId=20150110021451227105983711475801861&returnType=json&channelId=30000&macAddr=18%3Adc%3A56%3A36%3A67%3A9c
//		2015-07-22 14:31:00  [ main:26872 ] - [ INFO ]  Get请求结果:{"data":{"currentDate":"2015-07-22 14:29:09","systemDate":"1437546549103"},"status":{"code":200,"message":"系统错误200"}}

}
