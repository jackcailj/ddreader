package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.PublishArticleResponse;

import fitnesse.slim.SystemUnderTest;

public class PublishArticle extends FixtureBase{
	ReponseV2<PublishArticleResponse>   reponseResult; 
	@SystemUnderTest
	CreateBar bar = new CreateBar();	
	Logger logger = Logger.getLogger(QueryBarInfo.class);
	
	public ReponseV2<PublishArticleResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<PublishArticleResponse>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		String sql = "select bm.bar_id from bar_member as bm left join bar as b on bm.bar_id=b.bar_id "
				+ "where bm.cust_id="+login.getCustId()+" and bm.member_status!=4 and b.bar_status!=4 and b.article_num >=0"
				+ " ORDER BY rand() limit 1";
		String barId = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();
		//barId = "85";
		if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
			paramMap.put("barId","85");
		}
		String aSql=null;
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equals("FromDB")){
			aSql = "SELECT * FROM `article` where cust_id="+login.getCustId()
					+ " and is_show=1 and is_del=0 and bar_id="+barId+" ORDER BY RAND() limit 1";			
		}
		//查找被屏蔽的帖子的media digest id		
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equals("shield")){
			DbUtil.executeUpdate(Config.BOOKBARDBConfig, 
					"update article set is_show=0 and is_del=0 where cust_id="+login.getCustId()+" and bar_id="+barId+" LIMIT 1");
			Thread.sleep(1000);
			aSql = "SELECT * FROM `article` where cust_id="+login.getCustId()
					+ " and is_show=0 and is_del=0 and bar_id="+barId+" ORDER BY RAND() limit 1";
		}
		//查找被删除的帖子的media digest id		
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equals("deleted")){
			DbUtil.executeUpdate(Config.BOOKBARDBConfig, 
					"update article set is_show=1 and is_del=1 where cust_id="+login.getCustId()+" and bar_id="+barId+" LIMIT 1");
			Thread.sleep(1000);
			aSql = "SELECT * FROM `article` where cust_id="+login.getCustId()
					+ " and is_show=1 and is_del=1 and bar_id="+barId+" ORDER BY RAND() limit 1";
		}
		if(aSql!=null){
			String mediaDigestId = DbUtil.selectOne(Config.BOOKBARDBConfig, aSql).get("media_digest_id").toString();	
			paramMap.put("mediaDigestId",mediaDigestId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			String sql = "select * from media_digest where id="+reponseResult.getData().getMediaDigestId();
			MediaDigest digest = DbUtil.selectOne(Config.YCDBConfig, sql, MediaDigest.class);
			list1.add(digest.getTitle());
			list1.add(digest.getCardRemark());
			list2.add(paramMap.get("title"));
			list2.add(paramMap.get("content"));
			if(paramMap.get("imgUrls")!=null&&!(paramMap.get("imgUrls").isEmpty())){
				list1.add(digest.getSmallPic1Path());
				list2.add(paramMap.get("imgUrls"));
			}
			dataVerifyManager.add(new ListVerify(list1, list2,false));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
	
	//POST /media/api2.go?action=publishArticle&barId=4901&title=%E5%8F%91%E5%B8%96&content=%E5%91%A8%E4%B8%80&actionType=1 HTTP/1.1
	//{"data":{"currentDate":"2015-07-13 17:56:11","mediaDigestId":1340,"systemDate":"1436781371263"},"status":{"code":0},"systemDate":1436781371262}
}
