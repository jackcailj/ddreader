package com.dangdang.ucenter;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class UserIdSql {

    public  static  String GetPubIdByName(String userName) throws Exception {
        String selectSql="select t.ID from thirdparty_cust_id t\n" +
                "left join user_device u on t.CUST_ID=u.CUST_ID \n" +
                "where u.USERNAME='cailj_ddtest@126.com' limit 1";
        Map<String,Object> result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql);
        return ""+result.get("ID");
    }
}
