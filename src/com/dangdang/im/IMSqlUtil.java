package com.dangdang.im;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.im.meta.ImUser;

/**
 * Created by cailianjie on 2015-7-9.
 */
public class IMSqlUtil {

    /*
    通过custId获取ImUser信息
     */
    public  static ImUser getImUser(String custId) throws Exception {
        String selectString="select * from im_user where cust_id="+custId;
        ImUser imUser = DbUtil.selectOne(Config.IMDBConfig,selectString,ImUser.class);
        return imUser;

    }

    /*
    通过userName获取ImUser信息
     */
    public  static ImUser getImUserByName(String userName) throws Exception {
        String selectString="select * from im_user where user_name='"+userName+"'";
        ImUser imUser = DbUtil.selectOne(Config.IMDBConfig,selectString,ImUser.class);
        return imUser;

    }

}
