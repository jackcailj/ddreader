package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaCustomerSubscription;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class CustomerSubscribeDb {

    /*
    获取追更的内容
    type=1表示获取正在追更的，type=0表示取消追更的书籍
     */
    public static List<MediaCustomerSubscription> getCustomerSubscription(String custId,int type) throws Exception {
        String selectString="select * from media_customer_subscription where cust_id="+custId+" and status="+type+" ORDER BY creation_date DESC";
        List<MediaCustomerSubscription> mediaCustomerSubscriptions = DbUtil.selectList(Config.YCDBConfig,selectString,MediaCustomerSubscription.class);
        return mediaCustomerSubscriptions;
    }


    /*
   获取追更的内容
   type=1表示获取正在追更的，type=0表示取消追更的书籍
    */
    public static MediaCustomerSubscription getCustomerSubscription(String custId,String mediaId,String appId) throws Exception {
        String selectString="select * from media_customer_subscription where cust_id="+custId+" and media_id="+mediaId+" and app_id="+appId+" ORDER BY creation_date DESC";
        MediaCustomerSubscription mediaCustomerSubscriptions = DbUtil.selectOne(Config.YCDBConfig, selectString, MediaCustomerSubscription.class);
        return mediaCustomerSubscriptions;
    }
}
