package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.digital.RefreshCache;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.MediaColumnContent;

/**
 * 
 * @author guohaiying
 *
 */
public class MediaColumnContentDb {
	
	//获取频道栏目下频道列表 
	//MediaColumn.class used
    public static List<Channel> getChannelList(String columnCode, int num) throws Exception {       
    	String selectSQL = "SELECT channel.channel_id,channel.owner_id,channel.title,channel.description,channel.icon,channel.sub_number" +
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
       
	

	
	//获取频道栏目下频道列表
//    public static List<ChannelList> getChannelList(String columnCode, String custId, int num) throws Exception {
//       
//    	String selectSQL = "SELECT channel.channel_id,channel.owner_id,channel.title,channel.description,channel.icon,channel.sub_number	" +
//    			" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
//				" where column_code ='"+columnCode+"'"+
//				" and channel.shelf_status=1"+
//				" and channel.is_completed=1"+
//				" and  mcc.status in(1,2)"+
//				" and  now() between start_date and end_date"+
//				" order by mcc.status asc , IF(ISNULL(order_value),1,0) asc,order_value desc LIMIT "+num;      
//        List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//        List<ChannelList> channelList = new ArrayList<ChannelList>();
//        for(int i=0; i<infos.size(); i++){
//        	ChannelList tmp = new ChannelList();
//        	tmp.setChannelId(infos.get(i).get("channel_id").toString());
//        	tmp.setDescription(infos.get(i).get("description").toString());
//        	//设置hasBoughtMonthly  是否有包月权限  
//        	if(custId==null)
//        		tmp.setHasBoughtMonthly(0);  
//        	else if(hasBoughtMonthly(custId, infos.get(i).get("channel_id").toString())==0){
//        		tmp.setHasBoughtMonthly(0);  
//        	}else 
//        		tmp.setHasBoughtMonthly(1);  
//        	//设置type
//        	tmp.setOwnerType(getOwnerType(infos.get(i).get("owner_id").toString()));      	
//        	tmp.setIcon(infos.get(i).get("icon").toString());
//        	tmp.setSubNumber(infos.get(i).get("sub_number").toString());
//        	tmp.setTitle(infos.get(i).get("title").toString());       	
//        	channelList.add(tmp);
//        }
//        return channelList;
//    }
    
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
