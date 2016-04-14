package com.dangdang.db.digital;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.ChannelArticlesDigest;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author guohaiying
 */
public class ChannelArticlesDigestDb {

	//获取频道文章id  3:频道; 5:攻略;
	public static String getChannelDigest(String type) throws Exception{
		int _type = Integer.valueOf(type);
		String selectSQL = "SELECT  digest_id " +
				"FROM `channel_articles_digest` " +
				"WHERE is_publish=1 " +
				"AND channel_id IS NOT NULL " +
				"AND `status` IN (0, 1, 2) " +
				"AND digest_id IN(SELECT id " +
					"FROM media_digest " +
					"WHERE type="+type+")";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return null;
	}
	
	//获取用户的文章
	public static List<String> getUserChannelDigest(String custId) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL ="SELECT digest_id " +
				"FROM `channel_articles_digest` " +
				"WHERE channel_id=(SELECT channel_id " +
							"FROM `channel` WHERE cust_id="+_custId+") " +
				"AND `status` IN (0,1,2) AND is_publish=1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<String> digestIdList = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			digestIdList.add(infos.get(i).get("digest_id").toString());
		}
		return digestIdList;
	}
	
	//获取频道下的文章
	public static String getDigestIdByChannelId(String channelId) throws Exception{
		int _channelId = Integer.valueOf(channelId);
		String selectSQL = "SELECT digest_id FROM `channel_articles_digest` " +
				"WHERE channel_id="+_channelId+" AND is_publish=1 AND `status` IN (0,1)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		int n=(int)Math.random()*(infos.size()-1);
		return infos.get(n).get("digest_id").toString();
	}



    /*
    获取个人订阅频道文章列表
    Args:
        ctime:文章发布时间，返回发布时间大于ctime的文章，null返回所有
        custId:用户id
     */

    public static List<ChannelArticlesDigest> getHomeArticle(Date time,String custId,Boolean isFilter,int limit) throws Exception {
        String strDate="";
        if(time!=null) {
            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strDate = sdp.format(time);
        }

        String selectString="select cad.* from channel_articles_digest  cad\n" +
                "LEFT JOIN channel c on cad.channel_id=c.channel_id\n" +
                "where cad.is_publish=1 and publish_date<=NOW() and c.shelf_status=1 " +
				(isFilter?" and cad.is_homepage_to_all=1 ":"and  (case when cad.is_homepage_to_all=0 then "+(custId==null?"   FALSE ELSE TRUE END) ":" (cad.channel_id in (SELECT channel_id from channel_sub_user  where cust_id="+custId+"  ) and  (case  WHEN c.page_status=2 THEN FALSE ELSE  ( case WHEN c.page_status=0 THEN (case  when cad.`status`=1 and cad.homepage_status=1 then TRUE ELSE FALSE END ) ELSE TRUE END)  END )\t) ELSE TRUE end)\t\t\t\t\t\t\t\t\t\t\t\n"+
						"and cad.digest_id not in (select data_id from media_customer_remove_history where cust_id= "+custId+")"))
				+ (StringUtils.isBlank(strDate)?"":"  and cad.publish_date<'"+strDate+"'    \n") +
                " ORDER BY cad.publish_date DESC limit "+ limit;

        List<ChannelArticlesDigest> articlesDigests = DbUtil.selectList(Config.YCDBConfig,selectString,ChannelArticlesDigest.class);

        return articlesDigests;
    }
	
	public static String getRecordNum(String articleId) throws Exception{
		String selectSQL = "SELECT count(1) FROM `channel_articles_digest` WHERE articles_id="+articleId;
		Map<String, Object> infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL);
		return infos.get("count(1)").toString();
	}
	
}
