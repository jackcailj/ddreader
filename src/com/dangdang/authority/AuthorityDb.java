package com.dangdang.authority;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class AuthorityDb {

    /*
    获取购买的书籍
     */
    public static List<MediaAuthority> getUserEbook(String custId) throws Exception {
        String selectString = "select * from media_authority where cust_id =  "+custId +" and authority_type=1 ORDER BY last_modified_date desc";
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
    获取购买所有书籍、字体
     */
    public static List<MediaAuthority> getMediaAuthority(String custId) throws Exception {
        String selectString = "select * from media_authority where cust_id =  "+custId +" ORDER BY last_modified_date desc";
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
   获取购买的书籍信息
    */
    public static MediaAuthority getUserEbook(String custId,String productId) throws Exception {
        String selectString = "select * from media_authority where cust_id =  "+custId +" and authority_type=1 and product_id="+productId+" ORDER BY last_modified_date desc";
        MediaAuthority mediaAuthorities = DbUtil.selectOne(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


}
