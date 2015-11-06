package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.Article;
import com.dangdang.readerV5.reponse.ArticleInfo;

/**
 * 帖子详情接口
 * @author wuhaiyan
 */
public class QueryArticleInfo  extends FixtureBase{
	ReponseV2<ArticleInfo>   reponseResult;
	Map<String,Object> map = new HashMap<String,Object>();
	String mediaDigestId = null;
	 
	public ReponseV2<ArticleInfo> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ArticleInfo>>(){});
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		String sql = null;
		// 查找正常显示的帖子
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("FromDB")){
			sql = "select * from article where is_show=1 and is_del=0 limit 1";
		}
		// 查找被删除的帖子
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("shield")){
			sql = "select * from article where is_show=0 and is_del=0 limit 1";
		}
		// 查找被屏蔽的帖子
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("deleted")){
			sql = "select * from article where is_show=1 and is_del=1 limit 1";
		}
		if(sql!=null){
			try{
				map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
				mediaDigestId = map.get("media_digest_id").toString();
				paramMap.put("mediaDigestId", mediaDigestId);
			}
			catch(Exception e){
				throw new Exception("数据表中不存在符合条件的帖子id，此用例不执行");
			}
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			Article article = reponseResult.getData().getArticle();
			String sql ="SELECT * FROM `media_digest` where id="+mediaDigestId+" and is_show=1";
			Map<String,Object> digest = DbUtil.selectOne(Config.YCDBConfig, sql);
			Map<String, Object> userInfo = new HashMap<String, Object>();
			try{
				userInfo = DbUtil.selectOne(Config.UCENTERDBConfig, 
						"select cust_nickname, cust_img from login_record where cust_id="+map.get("cust_id").toString());
			}
			catch(Exception e){
				e.printStackTrace();
				userInfo = null;
			}
			
			List<String> list1 = new ArrayList<String>();	
			List<String> list2 = new ArrayList<String>();	
			list1.add(digest.get("bar_id").toString());
			list1.add(digest.get("content").toString());
			list1.add(userInfo!=null?userInfo.get("cust_nickname").toString().split("@")[0]:null);
			list1.add(mediaDigestId);
			list1.add(!(digest.get("title")==null||digest.get("title").toString().isEmpty())?digest.get("title").toString():null);
			
			try{
				CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, 
						"SELECT * FROM `comment_target_count` where target_id="+mediaDigestId, CommentTargetCount.class);
				list1.add(Integer.toString(count.getCommentCount()));
				list1.add(Integer.toString(count.getPraiseCount()));
			}
			catch(Exception e){
				//没有点赞和评论时，得到null，所以catch 空指针异常
				e.printStackTrace();
				list1.add("0");
				list1.add("0");
			}			
			list2.add(article.getBarId());
			list2.add(article.getContent());
			list2.add(userInfo!=null?article.getUserBaseInfo().getNickName():null);
			list2.add(article.getMediaDigestId());
			list2.add(article.getTitle()!=null?article.getTitle().toString():null);		
			list2.add(article.getCommentNum());
			list2.add(article.getPraiseNum());
						
			dataVerifyManager.add(new ValueVerify(list1, list2,false));
			super.dataVerify();			
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
