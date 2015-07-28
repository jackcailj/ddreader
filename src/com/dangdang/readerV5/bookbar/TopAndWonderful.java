package com.dangdang.readerV5.bookbar;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Article;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2Base;

public class TopAndWonderful extends FixtureBase{
	String subSql = "";
	String mediaDigestId = null;
	int top = -1;
	int wonderful = -1;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(paramMap.get("actionType")!=null&&paramMap.get("actionType").equalsIgnoreCase("top")){
			subSql = "is_top=0";
			top = 1;
		}
		if(paramMap.get("actionType")!=null&&paramMap.get("actionType").equalsIgnoreCase("cancelTop")){
			subSql = "is_top=1";
			top = 0;
		}
		if(paramMap.get("actionType")!=null&&paramMap.get("actionType").equalsIgnoreCase("wonderful")){
			subSql = "is_wonderful=0";
			wonderful = 1;
		}
		if(paramMap.get("actionType")!=null&&paramMap.get("actionType").equalsIgnoreCase("cancelWonderful")){
			subSql = "is_wonderful=1";
			wonderful = 0;
		}
		String sql = "SELECT bm.bar_id, bm.member_status, a.media_digest_id from bar_member as bm left join "
				+ "article as a on bm.bar_id=a.bar_id where bm. member_status=3 and bm.cust_id="+login.getCustId()
				+ " and a.is_show=1 and a.is_del=0 and a."+subSql+" ORDER BY RAND() limit 1";		
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("FromDB")){
			mediaDigestId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("media_digest_id").toString();	
		    paramMap.put("mediaDigestId", mediaDigestId);
		}		
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseV2Base = JSONObject.parseObject(result.toString(), ReponseV2Base.class);
		if(reponseV2Base.getStatus().getCode() == 0){	
			String sql = "select * from article where media_digest_id="+mediaDigestId;
			Article article = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Article.class);
			if(paramMap.get("actionType").contains("top")){
				dataVerifyManager.add(new ValueVerify<Integer>(top, article.getIsTop().intValue(),false));
			}
			if(paramMap.get("actionType").contains("wonderful")){
				dataVerifyManager.add(new ValueVerify<Integer>(wonderful, article.getIsWonderful().intValue(),false));
			}			
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}	
	
	//SELECT bm.bar_id, bm.member_status, a.media_digest_id from bar_member as bm left join article as a on bm.bar_id=a.bar_id 
	//where bm.member_status=3 and bm.cust_id=50232713 and a.is_show=1 and a.is_del=0 ORDER BY RAND() limit 1

}
