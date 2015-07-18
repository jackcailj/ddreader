package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Article;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ArticleList;
import com.dangdang.readerV5.reponse.ArticleListData;
import com.dangdang.readerV5.reponse.BarInfo;

public class QueryArticleList extends FixtureBase{
	ReponseV2<ArticleListData>   reponseResult;
	 String barId1 = "";
	 String barId2 = "";
	 
	public ReponseV2<ArticleListData> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ArticleListData>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
			super.setParameters(params);
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				String sql = "select bar_id from bar where bar_status!=4 and bar_id in "
					         + "(select bar_id from article where is_show=1 and is_del=0) ORDER BY rand() limit 1";
				barId1 = DbUtil.selectOne(Config.BOOKBARDBConfig, sql).get("bar_id").toString();	
				paramMap.put("barId", barId1);
			}
			if(paramMap.get("objectId")!=null&&paramMap.get("objectId").equals("FromDB")){
				String sql = "select br.bar_id, br.object_id from bar_relation as br left join "
						+ "bar as b on br.bar_id=b.bar_id where b.bar_status!=4 and b.bar_id in "
						+ "(select bar_id from article where is_show=1 and is_del=0) ORDER BY rand() limit 1";
				Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
				barId2 = map.get("bar_id").toString();	
				String objectId = map.get("object_id").toString();	
				paramMap.put("objectId", objectId);
			}
			if(paramMap.get("lastModifiedDateMsec")!=null&&paramMap.get("lastModifiedDateMsec").equalsIgnoreCase("currentTime")){
				paramMap.put("lastModifiedDateMsec", Long.toString(System.currentTimeMillis()));
			}
			if(paramMap.get("lastModifiedDateMsec")!=null&&paramMap.get("lastModifiedDateMsec").equalsIgnoreCase("FromDB")){
				String sql = "select * from article where is_show=1 and is_del=0 and bar_id ="+barId1+" "
						     + "ORDER BY last_modified_date_msec DESC";
				List<Article> article = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Article.class);
				paramMap.put("lastModifiedDateMsec", article.get(0).getLastModifiedDateMsec().toString());
			}
			if(paramMap.get("lastVisitDateMsec")!=null&&paramMap.get("lastVisitDateMsec").equalsIgnoreCase("currentTime")){
				paramMap.put("lastVisitDateMsec", Long.toString(System.currentTimeMillis()));
			}
			if(paramMap.get("lastVisitDateMsec")!=null&&paramMap.get("lastVisitDateMsec").equalsIgnoreCase("lastVisitTime")){
				Date date = new Date((System.currentTimeMillis()-1000*60*60*24));
				paramMap.put("lastVisitDateMsec", Long.toString(date.getTime()));
			}			
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = null;
			int defaultSize=50;
			if(paramMap.get("barId")!=null&&!(paramMap.get("barId").isEmpty())){
				long time=System.currentTimeMillis();
				if(paramMap.get("lastModifiedDateMsec")!=null&&!(paramMap.get("lastModifiedDateMsec").equals("null"))){
					time = Long.parseLong(paramMap.get("lastModifiedDateMsec"));
				}
				sql = "select * from article where is_show=1 and is_del=0 and bar_id ="+barId1+" "
						+ "and last_modified_date_msec <"+time+" ORDER BY is_top DESC, last_modified_date_msec DESC";
			}
			if(paramMap.get("objectId")!=null&&(!paramMap.get("objectId").isEmpty())&&paramMap.get("barId")==null){
				sql = "select * from article where is_show=1 and is_del=0 and bar_id ="+barId2+" "
						+ "ORDER BY is_top DESC, create_date DESC";
				// 单品页最多返回三条
				defaultSize = 3;
			}
			List<Article> article = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Article.class);
			List<ArticleList> list = reponseResult.getData().getArticleList();
			//帖子内容
			List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
			List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
			for(int i=0; i<reponseResult.getData().getArticleList().size(); i++){
				Map<String,String> map1 = new HashMap<String,String>();
				Map<String,String> map2 = new HashMap<String,String>();
				map1.put("articleId", article.get(i).getArticleId().toString());
				map1.put("barId", Long.toString(article.get(i).getBarId()));
				map2.put("articleId", list.get(i).getArticleId());
				map2.put("barId", list.get(i).getBarId());
				list1.add(map1);
				list2.add(map2);
			}
			dataVerifyManager.add(new ListVerify(list1, list2,false));
			
			//验证帖子数量
			if(article.size()>50){				
				dataVerifyManager.add(new ValueVerify<Integer>(50, list.size(),false));
			}
			else{
				dataVerifyManager.add(new ValueVerify<Integer>(article.size(), list.size(),false));
			}
			
			//updateArticleNum的值
			long lastVisitTime=0;
			if(paramMap.get("lastVisitDateMsec")!=null){
				lastVisitTime = Long.parseLong(paramMap.get("lastVisitDateMsec"));
			}
			else{
				lastVisitTime = System.currentTimeMillis();
			}
			//传objectId时，返回的结果没有updateArticleNum字段
			if(paramMap.get("objectId")==null){
				int updateArticleNum = 0;		
				sql = "select * from article where is_show=1 and is_del=0 and bar_id ="+barId1+" "
						+ " ORDER BY create_date DESC";
				article = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Article.class);
				for(int j=0; j<article.size(); j++){
					if(article.get(j).getLastModifiedDateMsec() > lastVisitTime){
						updateArticleNum++;
					}
				}						
				dataVerifyManager.add(new ValueVerify<Integer>(updateArticleNum, 
						              reponseResult.getData().getUpdateArticleNum(),false));		
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
