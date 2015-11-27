package com.dangdang.db.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.MyChannelList;

/**
 * 获取我的频道列表接口相关SQL
 * @author guohaiying
 *
 */
public class MyChannelListSQL {	
	//获取custId订阅的频道的总数
	public static String getUserChannelListTotal(String custId) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT COUNT(1) FROM `channel_sub_user` " +
				" WHERE type=1 " +
				" AND cust_id= "+_custId +
				" ORDER BY create_date";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		if(infos.get(0).get("COUNT(1)").toString().equals("0"))
			return "0";
		else
			return infos.get(0).get("COUNT(1)").toString();
	}
	

	
	//获取用户订阅的所有频道
	public static List<MyChannelList> getMyChannelList(String custId) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL="SELECT channel_id, description,icon,shelf_status,title FROM `channel` " +
				"WHERE is_completed=1 " +
				"AND channel_id IN (SELECT channel_id " +
				"FROM `channel_sub_user`" +
				" WHERE type=1 " +
				" AND cust_id= " + _custId +
				" ORDER BY create_date)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<MyChannelList> list = new ArrayList<MyChannelList>();
		for(int i=0;i<infos.size();i++){
			MyChannelList tmp = new MyChannelList();
			tmp.setChannelId(infos.get(i).get("channel_id").toString());
			tmp.setDescription(infos.get(i).get("description").toString());
			tmp.setIcon(infos.get(i).get("icon").toString());
			tmp.setShelfStatus(infos.get(i).get("shelf_status").toString());
			tmp.setTitle(infos.get(i).get("title").toString());
			list.add(tmp);
		}
		return list;
	}
	
	public static String getOneChannelWithCustid(String custid) throws Exception{
		List<MyChannelList> list = getMyChannelList(custid);
		return list.get(0).getChannelId();
	}
	
	public static void cancelAllChannel(String custId) throws Exception{
		List<MyChannelList> list = getMyChannelList(custId);
		for(int i=0; i<list.size(); i++){
			ChannelSubSQL.delSubWithCustidAndChannelid(custId, list.get(i).getChannelId());
		}
	}
	
	public static void main(String[] args) throws Exception{
		 List<MyChannelList> list = MyChannelListSQL.getMyChannelList("50098052");
		 for(int i=0; i<list.size(); i++){
			 System.out.println(list.get(i).getChannelId());
			 System.out.println(list.get(i).getDescription());
			 System.out.println(list.get(i).getIcon());
			 System.out.println(list.get(i).getShelfStatus());
			 System.out.println(list.get(i).getTitle());			 
		 }
	}
}
