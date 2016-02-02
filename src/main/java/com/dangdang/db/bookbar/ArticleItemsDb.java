package com.dangdang.db.bookbar;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dangdang.bookbar.meta.ArticleItems;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class ArticleItemsDb {
	
	/**
	 * 投票贴详情
	 * 
	 * */
	public static String getDigestId() throws Exception{
	    String sql = "select id from media_digest where is_show=1 and is_del=0 and id in "
						+ "(SELECT media_digest_id from bookbar.article_items) limit 10";;
		List<Map<String, Object>> list = DbUtil.selectList(Config.YCDBConfig, sql);
		return list.get((new Random()).nextInt(list.size())).get("id").toString();
	}
	
	/**
	 * 查找投票贴
	 * 
	 * */
	public static List<ArticleItems> getArticleItemsId(int count) throws Exception{
		String sql = "select * from article_items limit "+count;
		List<ArticleItems> items = DbUtil.selectList(Config.BOOKBARDBConfig, sql, ArticleItems.class);
		return items;
	}
	
	/**
	 * 查找投票贴
	 * 
	 * */
	public static List<ArticleItems> getArticleItemsId(String digestId) throws Exception{
		String sql = "select * from article_items where media_digest_id="+digestId;
		List<ArticleItems> items = DbUtil.selectList(Config.BOOKBARDBConfig, sql, ArticleItems.class);
		return items;
	}
	
	/**
	 * 查找用户已经投过票的帖子
	 * 
	 * */
	public static List<ArticleItems> getVotedItemsIdByUser(String custId) throws Exception{
		String sql = "select * from article_items where media_digest_id in"
				+ " (select media_digest_id from user_article_items where cust_id="+custId+")";
		List<ArticleItems> items = DbUtil.selectList(Config.BOOKBARDBConfig, sql, ArticleItems.class);
		return items;
	}
	
	/**
	 * 查找用户没有参与投票的帖子
	 * 
	 * */
	public static List<ArticleItems> getNoVotedItemsIdByUser(String custId) throws Exception{
		String sql = "select * from article_items where media_digest_id not in"
				+ " (select media_digest_id from user_article_items where cust_id="+custId+")";
		List<ArticleItems> items = DbUtil.selectList(Config.BOOKBARDBConfig, sql, ArticleItems.class);
		return items;
	}
	
	/**
	 * 查找某个投票贴
	 * 
	 * */
	public static ArticleItems getOneArticleItemsId(String id) throws Exception{
		String sql = "select * from article_items where article_items_id="+id;
		ArticleItems items = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, ArticleItems.class);
		return items;
	}
	

}
