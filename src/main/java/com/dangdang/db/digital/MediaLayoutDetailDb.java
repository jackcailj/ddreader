package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaLayoutDetail;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-28.
 */
public class MediaLayoutDetailDb {

    /*
    获取页面布局信息
    Args：
        code:页面布局code，参考MediaLayout
     */
    public static List<MediaLayoutDetail> getLayoutDetail(String code) throws Exception {
        String selectString = "select * from media_layout_detail where layout_code='"+code+"' order by order_value desc ";
        List<MediaLayoutDetail> details = DbUtil.selectList(Config.YCDBConfig,selectString, MediaLayoutDetail.class);

        return details;
    }


}
