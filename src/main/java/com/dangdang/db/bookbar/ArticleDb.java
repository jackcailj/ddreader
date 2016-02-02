package com.dangdang.db.bookbar;

import java.util.List;

import com.dangdang.bookbar.meta.Article;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class ArticleDb {
	
	/**
	 * 查找帖子信息	
	 * @param
	 *     show: 是否在客户端展示
	 *     del：是否已删除
	 *     type: 帖子类型(1:书评贴; 2:书吧贴; 31:图片投票贴;32:文字投票贴;41:书籍分享贴;42:笔记分享贴;43:频道分享贴;44:文章分享贴;45:吧分享贴;
	 *                 46:贴子分享贴;47:书单分享贴;48:专题分享贴;49:攻略分享贴;50:reader图片分享贴)
	 */
	public static List<Article> getArticle(String show, String del, String type, String custId) throws Exception{
		String sql = "select * from article where is_show="+show+" and is_del="+del+" and type="+type+
				      (custId.equals("null")?"":" and cust_id="+custId)+ " limit 2";
		List<Article> article = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Article.class);
		return article;
	}

}
