package com.dangdang.readerV5.bookbar;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountActionTypeInfo;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Article;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.param.model.ParseResult;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

public class DelArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	
	public ReponseV2<PublishArticleResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PublishArticleResponse>>(){});
	}
	
	static Integer mediaDigestId = null;
	static ILogin login;
	int experience;
	int integral;
	
	public int precondition() throws Exception{
		String param = "action=publishArticle&barId=&title=发个贴&content=测试发帖&cardType=0&actionType=1&token=&userName=whytest@dd.con&passWord=111111&loginType=email";
		Map<String,String> params = Util.generateMap(param);
		ParseResult parseResult=ParseParamUtil.parseParameter(params);
		params.putAll(Config.getCommonParam());
		login = parseResult.getLogin();		
		String sql = "select bm.bar_id from bar_member as bm left join bar as b on bm.bar_id=b.bar_id "
				+ "where bm.cust_id="+login.getCustId()+" and bm.member_status!=4 and b.bar_status=2"
				+ " limit 1";
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
			String sql = "select * from account_action_type_info where action_type_code='ZJSCTZ'";
			AccountActionTypeInfo info = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AccountActionTypeInfo.class);
			sql = "select * from attach_account where cust_id="+login.getCustId();
			AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
			experience = account.getAccountExperience() + info.getExperienceAward(); //info.getExperienceAward()为负数
			integral = account.getAccountIntegral() + info.getIntegralAward();//info.getIntegralAward()为负数
		}
		if(paramMap.get("mediaDigestId")!=null&&!(paramMap.get("mediaDigestId").isEmpty())
				&&!(paramMap.get("mediaDigestId").equals("aa"))){
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
			String sql = "select * from article where media_digest_id="+reponseResult.getData().getMediaDigestId()
					+" and cust_id="+login.getCustId();
			Article article = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Article.class);
			dataVerifyManager.add(new ValueVerify<String>(Integer.toString(article.getIsDel()), "1"));
			
			//删帖扣减经验和积分
			sql = "select * from attach_account where cust_id="+login.getCustId();
			AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
			dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountIntegral(), integral));
			dataVerifyManager.add(new ValueVerify<Integer>(account.getAccountExperience(), experience));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
	
}
