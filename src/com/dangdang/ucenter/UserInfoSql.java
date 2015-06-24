package com.dangdang.ucenter;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ucenter.meta.LoginRecord;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class UserInfoSql {

    public  static  String getPubIdByName(String userName) throws Exception {
        String selectSql="select t.ID from thirdparty_cust_id t\n" +
                "left join user_device u on t.CUST_ID=u.CUST_ID \n" +
                "where u.USERNAME='cailj_ddtest@126.com' limit 1";
        Map<String,Object> result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql);
        return ""+result.get("ID");
    }

    public  static LoginRecord getUserInfoByName(String userName) throws Exception {
        String selectSql="SELECT lr.* from login_record lr\n" +
                "left join user_device ud on lr.cust_id=ud.CUST_ID\n" +
                "where ud.USERNAME='cailj_ddtest@126.com' limit 1";
        LoginRecord result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql,LoginRecord.class);
        return result;
    }

    public  static String getCustIdByPubId(String pubId) throws Exception {
        String selectSql="SELECT CUST_ID from thirdparty_cust_id where id="+pubId;
        Map<String,Object> result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql);
        return ""+result.get("CUST_ID");
    }

    public  static LoginRecord getUserInfoByCustId(String custid) throws Exception {
        String selectSql="select lr_id,cust_id,cust_nickname,cust_img,device_no,device_type,login_token,create_date,last_update_date,introduct,login_platform,login_client,gender*1 as gender,bind_phone_num, display_id from login_record where cust_id="+custid;
        LoginRecord result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql,LoginRecord.class);
        return result;
    }
}
