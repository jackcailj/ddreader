package com.dangdang.authority;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.BorrowBookStatus;
import com.dangdang.digital.meta.Media;
import com.dangdang.readerV5.bookstore.Borrow;

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

    /*
    获取所有借阅过的书籍
     */
    public static List<BorrowAuthority> getAllBorrowMedias(String custId) throws Exception {
        String selectString = "select * from borrow_authority where cust_id="+custId+" order by creation_date  desc";
        List<BorrowAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig, selectString, BorrowAuthority.class);
        return mediaAuthorities;
    }

    /*
    获取所有借阅过但没有权限的书的书籍
     */
    public static List<BorrowAuthority> getBorrowNotBuyMedias(String custId,BorrowBookStatus borrowBookStatus) throws Exception {
        Long custIdl=Long.parseLong(custId);
        String selectString = "select b.* from borrow_authority_"+custIdl%16+" b \n" +
                "left join media_authority_"+custIdl%32+" m on b.cust_id=m.cust_id and b.product_id=m.product_id\n" +
                " where b.cust_id="+custId+" and m.product_id is null and "+borrowBookStatus.getFilter("b")+" order by b.creation_date  desc;";
        List<BorrowAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig, selectString, BorrowAuthority.class);
        return mediaAuthorities;
    }
}
