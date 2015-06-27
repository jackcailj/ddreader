package com.dangdang.account;

import com.dangdang.account.meta.AccountConsumeItems;
import com.dangdang.account.meta.AttachAccountItems;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class AccountUtils {

    public static AccountInfo getAccountInfo(String custId) throws Exception {
        String selectString="select m.cust_id as custId,m.user_name as userName,m.master_account_money as masterAccountMoney,m.master_account_money_ios as masterAccountMoneyIos,\n" +
                "a.account_grade as accountGrade,a.account_integral as accountIntegral, a.attach_account_money as attachAccountMoney,a.attach_account_money_ios as attachAccountMoneyIos from master_account m\n" +
                "LEFT JOIN attach_account  a on m.cust_id=a.cust_id where m.cust_id="+custId;

        AccountInfo info = DbUtil.selectOne(Config.ACCOUNTDBConfig,selectString,AccountInfo.class);
        return info;
    }

    /*
    获取用户铃铛主账户详情
    参数：
        custid：用户custid
        device：设备类型
    return：List<AccountConsumeItems>，没有值时返回空列表

     */
    public  static List<AccountConsumeItems> getMasterLingDangDetail(String custid,TestDevice device) throws Exception {
        return DbUtil.selectList(Config.ACCOUNTDBConfig,"select * from account_consume_items where  consume_source='"+device.toString()+"' and consume_type = 1000 and cust_id="+custid+" and account_type='"+AccountType.MASTER+"' ORDER BY consume_item_id desc ",AccountConsumeItems.class);
    }

    /*
    获取用户铃铛详情副账户列表
    参数：
        custid：用户custid
        device：设备类型
    return：List<AccountConsumeItems>，没有值时返回空列表

     */
    public  static List<AttachAccountItems> getAttachLingDangDetail(String custid,TestDevice device) throws Exception {
        return DbUtil.selectList(Config.ACCOUNTDBConfig,"select * from attach_account_items where cust_id="+custid+" and consume_source='"+device+"' and `status`=1 order by attach_account_items_id desc ",AttachAccountItems.class);
    }
}