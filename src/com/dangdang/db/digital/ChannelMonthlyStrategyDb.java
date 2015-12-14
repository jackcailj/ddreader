package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.ChannelMonthlyStrategy;


/**
 * 频道包月相关SQL
 * @author guohaiying
 *
 */
public class ChannelMonthlyStrategyDb {
	
	//根据频道Id获取 频道的包月策略
	//IChannelApiDubbo.java used
	public static List<ChannelMonthlyStrategy> getChannelMonthlyStrategy(String channelId) throws Exception{	
		String selectSQL = "SELECT id, type*1 as type, name, android, ios,original_price,channel_id,platform,new_price,max_times,strategy_days " +
			"FROM `channel_monthly_strategy` " +
			"WHERE channel_id="+channelId+" ORDER BY id";
		List<ChannelMonthlyStrategy> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL,ChannelMonthlyStrategy.class);		
		if(infos.size()==0) return null;		
		return infos;
	}
	
	//根据频道Id获取 频道的包月策略
	//IChannelApiDubbo.java used
	public static ChannelMonthlyStrategy getChannelMonthlyStrategy(String channelId, String strategyId) throws Exception{	
		int _strategyId = Integer.valueOf(strategyId);
		String selectSQL = "SELECT id, type*1 as type, name, android, ios,original_price,channel_id ,platform,new_price,max_times,strategy_days" +
			" FROM `channel_monthly_strategy` " +
			" WHERE channel_id="+channelId +
			" AND id = "+_strategyId;
		List<ChannelMonthlyStrategy> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL,ChannelMonthlyStrategy.class);		
		if(infos.size()==0) return null;		
		return infos.get(SqlUtil.getRandNum(infos));
	}
	
	//随机获取一个已配置包月策略的频道
	//IChannelApiDubbo wiki used
	//GetChannelIdParse.java used
	public static String getMonthlyStrategyChannel() throws Exception{
		String selectSQL = "SELECT channel_id " +
				"FROM `channel_monthly_strategy`" +
				"WHERE channel_id IN(SELECT channel_id " +
				"FROM channel " +
				"WHERE shelf_status =1 AND is_completed=1 AND is_allow_monthly=1)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();
	}
	
	//随机获取一个没有配置包月策略的频道
	//IChannelApiDubbo wiki used
	//GetChannelIdParse.java used
	public static String getNoMonthlyStrategyChannel() throws Exception{
		String selectSQL = "SELECT channel_id " +
				"FROM channel " +
				"WHERE shelf_status =1 AND is_completed=1 AND is_allow_monthly=0 AND channel_id NOT IN(" +
				"SELECT channel_id " +
				"FROM channel_monthly_strategy)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);		
		return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();
	}
	
	//获取支持包月且已下线的频道
	//GetChannelIdParse.java used
	public static String getMonthlyAndOfflineChannel() throws Exception{
		List<String> channelIDs = ChannelDb.getOfflineChannelList();
		String selectSQL = "SELECT DISTINCT(channel_id) " +
				"FROM `channel_monthly_strategy` " +
				"WHERE channel_id IN "+SqlUtil.getListToString(channelIDs);
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("channel_id").toString();
	}
	
		
	public static void main(String[] args) throws Exception{
		ChannelMonthlyStrategy aa = ChannelMonthlyStrategyDb.getChannelMonthlyStrategy("17", "238");
		String name = aa.getName();
		if(name.contains("天"))
			name = name.replace("天", "");
		System.out.println(Integer.valueOf(name));
		
	}

}
