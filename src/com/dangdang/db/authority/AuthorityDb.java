package com.dangdang.db.authority;

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
    public static List<MediaAuthority> getUserEbook(String custId,int num) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" and (authority_type =1 or (authority_type=2 and   order_no is not null and order_no!='' ))  limit  "+num;
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
    获取购买所有书籍、字体
     */
    public static List<MediaAuthority> getMediaAuthority(String custId) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" ORDER BY last_modified_date desc";
        List<MediaAuthority> mediaAuthorities = DbUtil.selectList(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


    /*
   获取购买的书籍信息
    */
    public static MediaAuthority getUserEbook(String custId,String productId) throws Exception {
        Integer nCustId=Integer.parseInt(custId);
        String selectString = "select * from media_authority_"+nCustId%32+" where cust_id =  "+custId +" and authority_type=1 and product_id="+productId+" ORDER BY last_modified_date desc";
        MediaAuthority mediaAuthorities = DbUtil.selectOne(Config.AUTHORITYConfig,selectString,MediaAuthority.class);
        return mediaAuthorities;
    }


}
