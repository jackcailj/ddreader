package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Article;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

public class DelArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	
	public ReponseV2<PublishArticleResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PublishArticleResponse>>(){});
	}
	
	static Integer mediaDigestId = null;
	static ILogin login;
	
	public int precondition() throws Exception{
		String param = "action=publishArticle&barId=85&title=发个贴&content=测试发帖&cardType=0&actionType=1&token=&userName=whytest@dd.con&passWord=111111&loginType=email";
		Map<String,String> params = Util.generateMap(param);
		ParseResult parseResult=ParseParamUtil.parseParameter(params);
		params.putAll(Config.getCommonParam());
		login = parseResult.getLogin();		
		String sql = "select bm.bar_id from bar_member as bm left join bar as b on bm.bar_id=b.bar_id "
				+ "where bm.cust_id="+login.getCustId()+" and bm.member_status!=4 and b.bar_status!=4"
				+ " ORDER BY rand() limit 1";
		String barId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();
		params.put("barId",barId);	
		// 发帖
		result = HttpDriver.doGet(Config.getUrl(), params,false);
		getResult();
		return reponseResult.getData().getMediaDigestId();
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(mediaDigestId==null){
			mediaDigestId = precondition();
		}
		//查找被屏蔽的帖子的media digest id		
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equals("shield")){
			DbUtil.executeUpdate(Config.BOOKBARDBConfig, 
					"update article set is_show=0 and is_del=0 where cust_id="+login.getCustId()+" and media_digest_id="+mediaDigestId);
			Thread.sleep(1000);
		}
		//查找被删除的帖子的media digest id		
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equals("deleted")){
			DbUtil.executeUpdate(Config.BOOKBARDBConfig, 
					"update article set is_show=1 and is_del=1 where cust_id="+login.getCustId()+" and media_digest_id="+mediaDigestId);
			Thread.sleep(1000);
		}
		if(paramMap.get("mediaDigestId")!=null&&!(paramMap.get("mediaDigestId").isEmpty())){
			paramMap.put("mediaDigestId",Integer.toString(mediaDigestId));
		}
		if(paramMap.get("token")!=null&&paramMap.get("token").equalsIgnoreCase("fromLogin")){
			paramMap.put("token",login.getToken());
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
//			String sql = "select * from media_digest where id="+reponseResult.getData().getMediaDigestId();
//			MediaDigest digest = DbUtil.selectOne(Config.YCDBConfig, sql, MediaDigest.class);
			
			String sql = "select * from article where media_digest_id="+reponseResult.getData().getMediaDigestId()
					+" and cust_id="+login.getCustId();
			Article article = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Article.class);
			
		//	dataVerifyManager.add(new ValueVerify<Boolean>(digest.getIsDel(), true));
			dataVerifyManager.add(new ValueVerify<Long>(Integer.toUnsignedLong(article.getIsDel()), 1l));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
	
	@Override
	 public boolean tearDown(){
		try {
			DbUtil.executeUpdate(Config.BOOKBARDBConfig, 
					"update article set is_show=1 and is_del=0 where cust_id="+login.getCustId()+" and media_digest_id="+mediaDigestId);
		if(mediaDigestId!=null){
			mediaDigestId = null;
		}
		if(login!=null){
			login = null;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.tearDown();		 
	 }
	

}
