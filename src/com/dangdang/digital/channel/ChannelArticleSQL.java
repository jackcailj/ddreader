package com.dangdang.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.ArticlePackage;

/**
 * 文集列表接口相关sql
 * @author guohaiying
 *
 */
public class ChannelArticleSQL {
	
	//获取有文集的频道id
//	public static String getChannelId() throws Exception{				
//		return ChannelSQL.getChannelHasArticles();
//	}
	
	public static List<ArticlePackage> getArticlePackageList(String channelId) throws Exception{
		int _channelId = Integer.valueOf(channelId);
		String selectSQL = "SELECT cad.articles_id, md.author, md.review_cnt, " +
				"md.card_remark, md.pic1_path, md.card_title, md.top_cnt " +
				"FROM `media_digest` md, channel_articles_digest cad " +
				"WHERE cad.digest_id=md.id " +
				"AND md.type=3 " +
				"AND cad.channel_id= " +_channelId +
				" AND cad.is_publish=1 " +
				"AND `status` IN (0,1) " +
				"ORDER BY cad.index_num ";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<ArticlePackage> list = new ArrayList<ArticlePackage>();
		for(int i=0; i<infos.size(); i++){
			ArticlePackage articlePackage = new ArticlePackage();
			articlePackage.setArticleId(infos.get(i).get("articles_id").toString());
			articlePackage.setAuthor(infos.get(i).get("author").toString());
			articlePackage.setCommentNum(infos.get(i).get("review_cnt").toString());
			articlePackage.setDescription(infos.get(i).get("card_remark").toString());
			articlePackage.setIcon(infos.get(i).get("pic1_path").toString());
			articlePackage.setTitle(infos.get(i).get("card_title").toString());
			articlePackage.setTopCnt(infos.get(i).get("top_cnt").toString());
			list.add(articlePackage);
		}
		return list;

	}
	
	

}
