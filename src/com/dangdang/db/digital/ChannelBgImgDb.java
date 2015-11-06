package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.UrlList;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelBgImgDb {
	//获取频道或书单的默认背景图地址
    public static List<UrlList> getBackImg(String type) throws Exception { 
    	int _type = Integer.valueOf(type);
    	String selectSQL = "SELECT img_id AS imgId, type,img_url AS imgUrl,creator FROM `channel_bg_img` WHERE type="+_type;
    	List<UrlList> backImgList = DbUtil.selectList(Config.YCDBConfig, selectSQL,UrlList.class);	
        return backImgList;
    }

}
