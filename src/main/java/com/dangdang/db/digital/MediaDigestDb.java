package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.StoreUpType;
import org.apache.commons.lang3.StringUtils;
import com.dangdang.digital.meta.MediaDigest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class MediaDigestDb {

    /*
    获取我的正常帖子列表
     */
    public static List<MediaDigest> getMediaDigest(String custId, StoreUpType type,Integer lastMediadistId) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt,\t\n" +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,\n" +
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where type in ("+type.getDigestType()+") and creator_cust_id ="+custId+" and is_del=0 and is_show=1 "+(lastMediadistId==null?"":" and id>"+lastMediadistId) +" ORDER BY create_date desc limit "+10 ;
        List<MediaDigest> medias = DbUtil.selectList(Config.YCDBConfig, selectString, MediaDigest.class);
        return medias;
    }

    /*
        获取我的发布的文章、帖子列表
 */
    public static List<MediaDigest> getMyMediaDigest(String custId, StoreUpType type) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt,\t\n" +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,\n" +
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where type in ("+type.getDigestType()+") and creator_cust_id ="+custId;
        List<MediaDigest> medias = DbUtil.selectList(Config.YCDBConfig, selectString, MediaDigest.class);
        return medias;
    }


    /*
    获取我创建的文章和帖子列表
     */
    public static List<MediaDigest> getMyCreateMediaDigest(String custId,int pageSize) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where creator_cust_id ="+custId+" and is_del=0 and is_show=1 and type in (3,4,5) ORDER BY id DESC limit "+pageSize ;
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }

    /*
              获取某个文章或帖子信息
     */
    public static MediaDigest getMediaDigest(int id) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book,creator_cust_id from media_digest where id="+id;
        MediaDigest mediaDigests = DbUtil.selectOne(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }


    /*
              根据帖子id获取帖子列表
     */
    public static List<MediaDigest> getMediaDigests(List<String> postIds) throws Exception {
        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title," +
                                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book,creator_cust_id from media_digest where is_del=0 and is_show=1 and id in("+StringUtils.join(postIds,",")+")";
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }

    /*
    获取某个文章或帖子信息
*/
    public static MediaDigest getMediaDigest(String id) throws Exception {
    	String selectString="select id,author,type*1 as type,card_title,card_remark,pic1_path " +
    			"from media_digest where id="+id;
    	MediaDigest mediaDigests = DbUtil.selectOne(Config.YCDBConfig,selectString,MediaDigest.class);
    	return mediaDigests;
    }

    /**
     * 当前时间到上次更新时间之间的翻篇儿新增文章条数
     * 当前时间到上次更新时间之间的抢先读新增文章条数
     * @param
     *       type: 文章类型，1. 翻篇儿  2 抢先读
     *       time： 当前时间
     * */
    public static  List<MediaDigest> getNewDigest(String type, String time) throws Exception{
    	String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show*1 as is_show,is_del*1 as is_del,sign_ids,day_or_night,mood*1,weight,operator,sort_page,is_paper_book*1 as is_paper_book from media_digest "
                + "where is_show=1 and is_del=0 and type="+type+" and last_update_date>'"+time+"'";
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString, MediaDigest.class);
        return mediaDigests;
    }

    /**
     * 获取要对其发表评论的目标主体
     * @param
     *       type: 文章类型，1. 翻篇儿  2 抢先读
     *       time： 当前时间
     * */
    public static  List<MediaDigest> getTargetId(String type) throws Exception{
    	String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt," +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,"+
                "is_show*1 as is_show,is_del*1 as is_del,sign_ids,day_or_night,mood*1,weight,operator,sort_page,is_paper_book*1 as is_paper_book from media_digest "
                + "where is_show=1 and is_del=0 and type="+type+" order by create_date DESC limit 20";
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString, MediaDigest.class);
        return mediaDigests;
    }


    //获取频道中的文章
    //type: 类型 1:翻篇儿; 2:抢先读; 3:频道; 4:贴子;5:攻略
    //SELECT * FROM `media_digest` WHERE id IN (SELECT  digest_id FROM `channel_articles_digest` WHERE is_publish=1 AND channel_id IS NOT NULL AND `status` IN (0, 1, 2) ORDER BY articles_id);
    public static  List<String> getDigestId(String type) throws Exception{
    	int _type = Integer.valueOf(type);
    	String selectString="SELECT id FROM digital.media_digest WHERE type="+_type+" AND is_del=0";
        List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectString);
        List ids = new ArrayList<String>();
        for(int i=0;i<infos.size();i++){
        	ids.add(infos.get(i).get("id").toString());
        }
        return ids;

    }

	//选取有效地type类型的文章
    //CommentTargetCountDb.java used
    //类型 3:频道; 5:攻略
	public static List<String> getDigest(int type) throws Exception{
		String selectSQL = "SELECT d.id "+
                " FROM"+
                	" digital.media_digest d,"+
                	" digital.channel_articles_digest cad,"+
                	" digital.channel c"+
                " WHERE d.id = cad.digest_id"+
                " AND cad.channel_id = c.channel_id"+
                " AND cad. STATUS IN ('0', '1')"+
                " AND c. STATUS IN ('0', '1')"+
                " AND c.shelf_status = '1'  AND d.is_show=1 AND d.is_del=0 AND d.type="+type+
                " AND cad.is_publish = 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<String> list = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			list.add(infos.get(i).get("id").toString());
		}
		return list;
	}

    //攻略
    //SELECT * FROM `comment_target_count` WHERE target_source=7000 ORDER BY browse_count DESC

    //获取用户的某个攻略/文章
    //type: 类型 3:频道; 5:攻略
    public static String getUserDigestId(String custId, String type) throws Exception{
    	int _type = Integer.valueOf(type);
    	String selectString = "SELECT id FROM `media_digest` " +
    			"WHERE type ="+_type+" AND id IN " +
    			SqlUtil.getListToString(ChannelArticlesDigestDb.getUserChannelDigest(custId));
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectString);
		int n = (int) (Math.random()*(infos.size()-1));
    	return infos.get(n).get("id").toString();
    }
    /**
     * 获取书友圈的文章
     * @param
     *       custId:用户id
     *       limit： 取几条记录
     *       date： create date时间
     * */
    public static  List<Map<String, Object>> getDigestOfBookFriend(String custId, String limit, String date) throws Exception{
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	String selectString = "SELECT id,title,type*1 as type FROM media_digest "
    			            + "where (creator_cust_id in "
    			            + "(select passive_user_id from ucenter.book_firend bf1 where bf1.active_user_id="+custId
    			            + " UNION select active_user_id from ucenter.book_firend bf2 where  bf2.passive_user_id="+custId+") or creator_cust_id= "+custId+")"
    			            + " and is_show=1 and is_del=0 and create_date"+date+" and show_start_date<'"+df.format(new Date())
  			                +"' and make_type!=1 ORDER BY create_date DESC limit "+limit;
    	List<Map<String, Object>> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString);
        return mediaDigests;
    }


    /**
     * 获取书友圈的文章
     * @param
     *       custId:用户id
     *       limit： 取几条记录
     *       date： create date时间
     * */
    public static  List<Map<String, Object>> getAttendDigestList(String custId, String limit, String date) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String selectString = " SELECT * FROM media_digest md" +
                "\t\t\t\tLEFT JOIN  (select digest_id from channel_sub_user  csu\n" +
                "LEFT JOIN channel_articles_digest cad on csu.channel_id=cad.channel_id where cust_id="+custId+") as subDigest on md.id=subDigest.digest_id\n" +
                "\t\t\t\tLEFT JOIN (\n" +
                "                select\n" +
                "                    passive_user_id \n" +
                "                from\n" +
                "                    ucenter.book_firend bf1 \n" +
                "                where\n" +
                "                    bf1.active_user_id= \n" +custId+
                "                UNION\n" +
                "                select\n" +
                "                    active_user_id \n" +
                "                from\n" +
                "                    ucenter.book_firend bf2 \n" +
                "                where\n" +
                "                    bf2.passive_user_id=\n" +custId+
                "            ) as attentUser on md.creator_cust_id=attentUser.passive_user_id\n" +
                "    where\n" +
                "        \n" +
                "    (subDigest.digest_id is not null or attentUser.passive_user_id is not null or md.creator_cust_id=" +custId +")"+
                "    \n" +
                "    and is_show=1 \n" +
                "    and is_del=0 \n" +
                "    and create_date" +date+
                "    and show_start_date<NOW() \n" +
                "    and make_type!=1 \n" +
                "ORDER BY\n" +
                "    create_date DESC limit "+limit;
        List<Map<String, Object>> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString);
        return mediaDigests;
    }

    /**
     * 获取书友圈的文章
     * @param
     *       custId:用户id
     *       limit： 取几条记录
     *       date： create date时间
     * */
    public static  List<MediaDigest> getAttendDigestList(String custId, List<String> attendUserList, String limit, String date) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String selectString = " SELECT md.* FROM media_digest md" +
                " left join channel_articles_digest cad on md.id=cad.digest_id"+
                " left join channel_articles ca on cad.articles_id=ca.id"+
                "    where\n" +
                "        \n" +
                "    (md.creator_cust_id=" +custId +" or md.creator_cust_id in ("+StringUtils.join(attendUserList,",")+"))"+
                "    and ca.is_publish=1 \n" +
                "    and is_show=1 \n" +
                "    and md.is_del=0 \n" +
                "    and md.create_date" +date+
                //"    and show_start_date<NOW() \n" +
                "    and md.make_type!=1 \n" +
                "ORDER BY\n" +
                "    md.create_date DESC limit "+limit;
        List<MediaDigest> mediaDigests = DbUtil.selectList(Config.YCDBConfig,selectString,MediaDigest.class);
        return mediaDigests;
    }

    /*
    获取文章
     */
    public static List<MediaDigest> getMediaDigest(StoreUpType storeUpType, BookStatus bookStatus, List<String> DigestIdList, boolean isIn, int number) throws Exception {

        String selectString="select id,author,media_id,media_chapter_id,media_name,bar_id,first_catetory_id,first_catetory_name,content,type*1 as type,column_id,column_name,stars,review_cnt,collect_cnt,share_cnt,\t\n" +
                "click_cnt,top_cnt,card_title,card_remark,card_type*1 as card_type,pic1_path,small_pic1_path,small_pic2_path,small_pic3_path,show_start_date,create_date,title,\t\n" +
                "is_show,is_del,sign_ids,day_or_night,mood,weight,operator,sort_page,is_paper_book from media_digest where "+(storeUpType.getDigestType()==""?" 1=1 ":" type in("+storeUpType.getDigestType()+")")+
                (bookStatus==BookStatus.VALID?" and is_del=0 ":" and is_del=1 ")+
                (DigestIdList.size()==0?"":" and id "+(isIn?" in ":"not in ")+"("+ StringUtils.join(DigestIdList, ",")+")")+
                " limit "+number;
        List<MediaDigest> mediaDigest = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, MediaDigest.class);
        return mediaDigest;
    }

    //获取支持或不支持打赏的频道文章或攻略
    //type 3：频道 5：攻略
    //isSupportReward 1：支持打赏  0：不支持打赏
    public static String getDegistId(int type, int isSupportReward) throws Exception{
    	String selectSQL="SELECT digest_id " +
    			"FROM `channel_articles_digest` " +
    			"WHERE is_publish=1 AND `status` IN (0,1) " +
    			"AND channel_id IN (SELECT channel_id FROM channel WHERE shelf_status=1) " +
    			"AND digest_id IN (SELECT id FROM `media_digest` WHERE type=5 AND is_support_reward=1)";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
    	List<String> degistIds = new ArrayList<String>();
    	for(int i=0; i<infos.size(); i++){
    		degistIds.add(infos.get(i).get("digest_id").toString());
    	}
    	return degistIds.get(SqlUtil.getRandNum(degistIds));
    }

    public static void main(String[] args){
    	String s;
		try {
			s = MediaDigestDb.getUserDigestId("50098052","3");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
