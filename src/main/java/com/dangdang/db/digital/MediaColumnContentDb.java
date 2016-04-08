package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.digital.RefreshCache;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.MediaColumnContent;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.digital.meta.MediaTagLink;

/**
 * 
 * @author guohaiying
 *
 */
public class MediaColumnContentDb {
	
	//获取频道栏目下频道列表 
	//MediaColumn.class used
    public static List<Channel> getChannelList(String columnCode, int num) throws Exception {       
    	String selectSQL = "SELECT channel.*" +
    			" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
				" where column_code ='"+columnCode+"'"+
				" and channel.shelf_status=1"+
				" and channel.is_completed=1"+
				" and  mcc.status in(1,2)"+
				" and  now() between start_date and end_date"+
				" order by mcc.status asc , IF(ISNULL(order_value),1,0) asc,order_value desc LIMIT "+num;      
        List<Channel> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Channel.class);	
        return infos;
    }
       
	//获取xx栏目下书
	public static List<String> getColumnContent(String columnCode) throws Exception{
		String selectSQL = "SELECT sale_id " +
								"FROM `media_column_content` " +
								"WHERE column_code='"+columnCode+"' " +
								"AND `status` in (1,2) " +
								"AND end_date> NOW()";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<String> saleIdList = new  ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			saleIdList.add(infos.get(i).get("sale_id").toString());
			System.out.println("dbList: "+ infos.get(i).get("sale_id"));
		}
		return saleIdList;
	}

    
    //获取某个栏目下的所有有效频道
    public static List<MediaColumnContent> getChannelList(String columnCode) throws Exception{
       	String selectSQL = "SELECT mcc.* " +
       		" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
       		" where mcc.column_code ='"+columnCode+"'"+
       		" and channel.shelf_status=1"+
       		" and  mcc.status in(1,2)"+
       		" and  now() between start_date and end_date";  
		List<MediaColumnContent>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumnContent.class);	
		return infos;	
    }


	//获取某个栏目下的所有内容
	public static List<MediaColumnContent> getMediaColumnContent(String columnCode) throws Exception{
		String selectSQL = "SELECT mcc.*" +
				" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
				" where mcc.column_code ='"+columnCode+"'"+
				" and channel.shelf_status=1"+
				" and  mcc.status in(1,2)"+
				" and  now() between start_date and end_date";
		List<MediaColumnContent>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumnContent.class);
		return infos;
	}


    //获取某个栏目下的标签
    public static List<MediaTagLink> getMediaColumnTagLinkList(String columnCode) throws Exception{
        String selectSQL ="SELECT mtl.* from media_tag_link mtl \n" +
                "left join media_column_content  mcc on mtl.id=mcc.sale_id\n" +
                "where mcc.`status` in(1,2) and mcc.column_code = '"+columnCode+"' and NOW() BETWEEN mcc.start_date and mcc.end_date order by mcc.`status` asc, mcc.order_value desc ";
        List<MediaTagLink>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaTagLink.class);
        return infos;
    }


    //获取某个栏目下的文章
    public static List<MediaDigest> getMediaColumnDigestList(String columnCode,int limitNum) throws Exception{
        String selectSQL ="SELECT md.id,md.author,md.media_id,md.media_chapter_id,md.media_name,md.bar_id,md.first_catetory_id,md.first_catetory_name,md.content,type*1 as type,md.column_id,md.column_name,md.stars,md.review_cnt,md.collect_cnt,md.share_cnt," +
                "md.click_cnt,md.top_cnt,md.card_title,md.card_remark,md.card_type*1 as card_type,md.pic1_path,md.small_pic1_path,md.small_pic2_path,md.small_pic3_path,md.show_start_date,md.create_date,md.title," +
                "md.is_show*1 as is_show,md.is_del*1 as is_del,md.sign_ids,md.day_or_night,md.mood*1,md.weight,md.operator,md.sort_page,md.is_paper_book*1 as is_paper_book  from media_digest md " +
                "left join media_column_content  mcc on md.id=mcc.sale_id\n" +
                "where mcc.`status` in(1,2) and mcc.column_code = '"+columnCode+"' and NOW() BETWEEN mcc.start_date and mcc.end_date order by mcc.`status` asc, mcc.order_value desc limit "+limitNum;

        List<MediaDigest>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaDigest.class);
        return infos;
    }

    

	//随机返回xx栏目下的一个频道
	public static int getRandChannel(String columnCode) throws Exception{
		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content`" +
				" WHERE column_code='" +columnCode + "'" +
						" AND `status` in (1,2) " +
						"AND end_date>NOW()  " +
						"AND sale_id IN (" +
						"SELECT channel_id " +
						"FROM channel " +
						"WHERE shelf_status=1 " +
						"AND is_completed=1)" +
						" order by rand() limit 1";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		Map<String, Object> m = result.get(0);
		return Integer.parseInt(m.get("sale_id").toString());
	}	
	
	//返回最高排序值
	public static int setSortWithColumncode(String columncode) throws Exception{
		//setNormal(columncode);
		//随机获取一本书
		int sale_id = getRandChannel(columncode);		
		String selectSQL = "SELECT MAX(order_value) FROM `media_column_content` WHERE column_code='"+columncode+"'";
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		Map<String, Object> m = result.get(0);
		int value = Integer.valueOf(m.get("MAX(order_value)").toString());		
		String updateSQL = "UPDATE `media_column_content` SET order_value = "+ (value+1)+" WHERE sale_id=" + sale_id;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);		
		RefreshCache.refresh();
		return sale_id;
	}
	
	public static void main(String[] args){
		try {
			List<MediaColumnContent> list = MediaColumnContentDb.getChannelList("all_rec_pdzbtj");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
