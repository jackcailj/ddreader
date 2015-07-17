package com.dangdang.readerV5.bookbar;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
			sql = "select * from article where is_show=1 and is_del=0 ORDER BY RAND() limit 1";
		}
		// 查找被删除的帖子
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("shield")){
			sql = "select * from article where is_show=0 ORDER BY RAND() limit 1";
		}
		// 查找被屏蔽的帖子
		if(paramMap.get("mediaDigestId")!=null&&paramMap.get("mediaDigestId").equalsIgnoreCase("deleted")){
			sql = "select * from article where is_del=1 ORDER BY RAND() limit 1";
		}
		if(sql!=null){
			map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
			if(map.get("media_digest_id")==null){
				throw new Exception("没有查找到'"+paramMap.get("mediaDigestId").toString()+"'帖子id");
			}
			mediaDigestId = map.get("media_digest_id").toString();
			paramMap.put("mediaDigestId", mediaDigestId);
		}
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			Article article = new Article();
			String sql ="SELECT * FROM `media_digest` where id="+mediaDigestId+" and is_show=1";
			Map<String,Object> digest = DbUtil.selectOne(Config.YCDBConfig, sql);
			Map<String, Object> userInfo = DbUtil.selectOne(Config.UCENTERDBConfig, 
					"select cust_nickname, cust_img from login_record where cust_id="+map.get("cust_id").toString());
			article.setContent(digest.get("content").toString());
			// 返回信息里的custid是加密的，为了便于下边比较，做如下设置
			article.setCustId(reponseResult.getData().getArticle().getCustId());
			article.setHeadPhoto(userInfo.get("cust_img").toString());
			// 不登陆, 不知道是哪个用户, 不知道是否点过赞, 都显示0
			// 登录账号（该账号对该贴点赞过）， 获取详情时，isPraise是1
			// 另外一个账号登录（没有对该贴点赞过），获取详情时，isPraise是0			
			article.setIsPraise("0");
			article.setIsTop(map.get("is_top").toString());
			article.setIsWonderful(map.get("is_wonderful").toString());
			article.setLastModifiedDateMsec(map.get("last_modified_date_msec").toString());
			article.setMediaDigestId(mediaDigestId);
			article.setNickName(userInfo.get("cust_nickname").toString().split("@")[0]);
			article.setTitle(digest.get("title").toString());
			int index = reponseResult.getData().getArticle().getHeadPhoto().lastIndexOf("?");
			String subStr = reponseResult.getData().getArticle().getHeadPhoto().substring(0, index);
			reponseResult.getData().getArticle().setHeadPhoto(subStr);
			dataVerifyManager.add(new ValueVerify(article, reponseResult.getData().getArticle(),true));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
