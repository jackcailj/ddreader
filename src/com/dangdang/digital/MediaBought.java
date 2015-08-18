package com.dangdang.digital;

import com.dangdang.autotest.common.PlatForm;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBoughtDetail;

import java.util.List;

/**
 * 购买相关sql,
 * Created by cailianjie on 2015-7-8.
 */
public class MediaBought {

    /*
    获取我的已购列表
    参数：
        PlatForm：只能为PlatForm.DS或者PlatForm.YC
     */
    public  static List<MediaBought> getBoughtList(String custId,PlatForm platForm,int start,int end) throws Exception {
        String selectString="SELECT * from media_bought where cust_id="+custId+(platForm==PlatForm.YC?" and whole_flag=0 ":"")+" order by bought_id DESC limit "+start+","+(end+1);
        List<MediaBought> boughts = DbUtil.selectList(Config.YCDBConfig,selectString,MediaBought.class);
        return boughts;
    }


    /*
   获取我的已购详情
   参数：
       PlatForm：只能为PlatForm.DS或者PlatForm.YC
    */
    public  static List<MediaBoughtDetail> getBoughtDetailList(String boughtId,int start,int end) throws Exception {
        String selectString="select * from media_bought_detail where bought_id="+boughtId+" order by bought_detail_id desc limit "+start+","+(end+1);
        List<MediaBoughtDetail> boughts = DbUtil.selectList(Config.YCDBConfig,selectString,MediaBoughtDetail.class);
        return boughts;
    }
}
