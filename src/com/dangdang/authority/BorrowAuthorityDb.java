package com.dangdang.authority;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class BorrowAuthorityDb {

    /*
    获取未过期的借阅权限
     */
    public  static List<BorrowAuthority> getBorrowMedias(String custId) throws Exception {
        String selectString = "select * from borrow_authority where cust_id="+custId+" and UNIX_TIMESTAMP(NOW())*1000<deadline order by creation_date  desc";
        List<BorrowAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig, selectString, BorrowAuthority.class);
        return mediaAuthorities;
    }
}
