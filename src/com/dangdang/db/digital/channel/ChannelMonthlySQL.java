package com.dangdang.db.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.model.ChannelMonthlyStrategy;

/**
 * 频道包月相关SQL
 * @author guohaiying
 *
 */
public class ChannelMonthlySQL {
	
	//
	public static List<ChannelMonthlyStrategy> getChannel(String channelId) throws Exception{
	
		Long _channelId = Long.valueOf(channelId);
		String selectSQL = "SELECT id, name,type*1 as type, android, ios,original_price " +
			"FROM `channel_monthly_strategy` " +
			"WHERE channel_id="+channelId+" ORDER BY id";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		
		if(infos.size()==0)return null;
		List<ChannelMonthlyStrategy> list = new ArrayList<ChannelMonthlyStrategy>();
		for(int i=0;i<infos.size();i++){
			ChannelMonthlyStrategy strategy = new ChannelMonthlyStrategy();
			Long id = Long.valueOf(infos.get(i).get("id").toString());
			strategy.setId(id);
			strategy.setType(Integer.valueOf(infos.get(i).get("type").toString()));
			strategy.setName(infos.get(i).get("name").toString());
			Long android = Long.valueOf(infos.get(i).get("android").toString());
			strategy.setAndroid(android);
			Long ios = Long.valueOf(infos.get(i).get("ios").toString());
			strategy.setIos(ios);
			Long originalPrice = Long.valueOf(infos.get(i).get("original_price").toString());
			strategy.setOriginalPrice(originalPrice);
			strategy.setChannelId(_channelId);
			list.add(strategy);
		}
		return list;
	}
	
	//随机获取一个已配置包月策略的频道
	public String getMonthlyChannel() throws Exception{
		String selectSQL = "SELECT channel_id " +
				"FROM `channel_monthly_strategy` " +
				"ORDER BY RAND() " +
				"LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("channel_id").toString();
	}
	
	//随机获取一个没有配置包月策略的频道
	public String getNoMonthlyChannel() throws Exception{
//		String selectSQL="SELECT channel_id FROM channel " +
//				" WHERE channel_id NOT IN(" +
//				" SELECT channel_id " +
//				" FROM `channel_monthly_strategy`) " +
//				" ORDER BY RAND() " +
//				" LIMIT 1";
		String selectSQL = "SELECT channel_id " +
				"FROM `channel` " +
				"WHERE is_allow_monthly=0 ORDER BY RAND() LIMIT 1;";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("channel_id").toString();
	}
	
	//随机获取频道下的一本书mediaId
	public String getMediaId(String channelId) throws Exception{
		Long _channelId = Long.valueOf(channelId);
		String selectSQL = "SELECT media_id FROM `media_booklist` mb, media_booklist_detail mbd , channel c " +
				" WHERE c.channel_id=mb.channel_id " +
				" AND mb.booklist_id=mbd.booklist_id " +
				" AND on_shelf_status=1 " +
				" AND c.channel_id="+_channelId +
				" AND mbd.media_id IS NOT NULL" +
				" ORDER BY RAND() " +
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("media_id").toString();
	}
	
	public static void main(String[] args) throws Exception{
		List<ChannelMonthlyStrategy> strategy = ChannelMonthlySQL.getChannel("17");
		System.out.println("aaaa"+strategy.get(0).getChannelId());
		System.out.println("aaaa"+strategy.get(0).getType());
		System.out.println("aaaa"+strategy.get(0).getName());
		System.out.println("aaaa"+strategy.get(0).getAndroid());
		System.out.println("aaaa"+strategy.get(0).getIos());
		System.out.println("aaaa"+strategy.get(0).getOriginalPrice());
	}

}
