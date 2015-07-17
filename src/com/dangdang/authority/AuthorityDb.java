package com.dangdang.authority;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class AuthorityDb {

    /*
    获取购买的所有东西
     */
    public static List<MediaAuthority> getUserEbook(String custId) throws Exception {
        String selectString = "select * from media_authority where cust_id =  "+custId;
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }
}
