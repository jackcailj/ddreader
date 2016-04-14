package com.dangdang.db.digital;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBought;
import com.dangdang.digital.meta.MediaBoughtDetail;

import java.util.List;

/**
 * 购买相关sql,
 * Created by cailianjie on 2015-7-8.
 */
public class MediaBoughtDb {

    /*
    获取我的已购列表
    参数：
        PlatForm：只能为PlatForm.DS或者PlatForm.YC
     */
    public  static List<MediaBought> getBoughtList(String custId, PlatForm platForm, int start, int end) throws Exception {
        String selectString="SELECT * from media_bought where cust_id="+custId+(platForm==PlatForm.YC?" and whole_flag=0 ":"")+" GROUP BY cust_id,media_id  order by bought_id DESC limit "+start+","+(end+1);
        List<MediaBought> boughts = DbUtil.selectList(Config.YCDBConfig,selectString,MediaBought.class);
        return boughts;
    }


    /*
   获取我的已购详情
   参数：
       PlatForm：只能为PlatForm.DS或者PlatForm.YC
    */
    public  static List<MediaBoughtDetail> getBoughtDetailList(List<String> boughtIds,int start,int end) throws Exception {
        String selectString="select * from media_bought_detail where bought_id in ("+ StringUtils.join(boughtIds,",")+") order by bought_detail_id desc limit "+start+","+(end+1);
        List<MediaBoughtDetail> boughts = DbUtil.selectList(Config.YCDBConfig,selectString,MediaBoughtDetail.class);
        return boughts;
    }


    /*
  根据boughtId获取同一本书的购买记录，针对非全本购买数据
  参数：
      PlatForm：只能为PlatForm.DS或者PlatForm.YC
   */
    public  static List<MediaBought> getBoughtIds(String custId,String boughtId) throws Exception {
        String selectString="select bought_id from media_bought where cust_id="+custId+" and media_id in (select media_id from media_bought WHERE bought_id="+boughtId+")";
        List<MediaBought> boughts = DbUtil.selectList(Config.YCDBConfig,selectString,MediaBought.class);
        return boughts;
    }
}
