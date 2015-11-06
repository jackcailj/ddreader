package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelOwner {
	
    //根据owner_id获取type
	//MediaColumnContentDb.class used
    public static String getOwnerType(String ownerId) throws Exception{
    	int _ownerId = Integer.valueOf(ownerId);
    	String selectSQL="SELECT type FROM `channel_owner` WHERE owner_id=" + _ownerId;
    	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	if(infos.size()==0)
    		return null;
    	else
    		return infos.get(0).get("type").toString();	
    }

}
