package com.dangdang.db.digital;

import com.dangdang.autotest.common.PlatForm;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaActivityInfo;
import com.dangdang.reader.functional.reponse.PayMent;

import java.util.List;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class MediaActivityInfoDb {

    public static List<PayMent> getPayment(PlatForm platForm) throws Exception {
        String selectString ="select max(deposit_gift_read_price) maxGiving ,activity_type_id paymentId,\n" +
                "(case activity_type_id \n" +
                "\t\twhen 1018 then '支付宝充值' \n" +
                "\t\twhen 1016 then '财付通充值' \n" +
                "\t\twhen 1017 then '微信支付充值' \n" +
                "\t\twhen 1019 then '短信支付充值' \n" +
                "when 1014 then 'IOS充值'" +
                "when 1015 then 'IPAD充值'"+
                 "end\n" +
                ") payment\n" +
                " from media_activity_info where from_paltform='"+platForm.toString()+"' and status=1 and end_time>NOW() GROUP BY activity_type_id order by activity_id desc";

        return DbUtil.selectList(Config.YCDBConfig,selectString,PayMent.class);
    }


    /*
    获取mediaActivetyInfo信息
     */
    public static List<MediaActivityInfo> getMediaActivityInfo(PlatForm platForm,String activityTypeId) throws Exception {
        String selectString ="select * from media_activity_info where from_paltform='"+platForm.toString()+"' and activity_type_id="+activityTypeId+" and status=1 and end_time>NOW()  order by activity_id ";

        return DbUtil.selectList(Config.YCDBConfig,selectString,MediaActivityInfo.class);
    }
}
