package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.MediaBooklist;


/**
 * 
 * @author guohaiying
 *
 */
public class MediaBooklistDb {
	
    //获取某频道的书单信息
    //Channel.java used
    public static MediaBooklist getChannel(String channelId) throws Exception { 
    	long _channelId = Long.valueOf(channelId);
    	String selectSQL = "SELECT * FROM `media_booklist` WHERE channel_id="+_channelId;
    	List<MediaBooklist> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaBooklist.class);	
        return infos.get(0);
    }


}
