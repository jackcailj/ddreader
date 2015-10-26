package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaStoreup;
import com.dangdang.enumeration.StoreUpType;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-31.
 */
public class StoreUpSQL {

    /*
    获取收藏列表
     */
    public  static List<MediaStoreup> getStoreUpList(String custId,StoreUpType storeUpType) throws Exception {
        String selectString ="select * from media_storeup where cust_id="+custId+" and type='"+storeUpType.toString()+"' order by store_date desc";
        List<MediaStoreup> mediaStoreups = DbUtil.selectList(Config.YCDBConfig,selectString,MediaStoreup.class);

        return mediaStoreups;
    }
}
