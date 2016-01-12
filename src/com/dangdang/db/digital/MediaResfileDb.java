package com.dangdang.db.digital;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaResfile;
import com.dangdang.digital.meta.MediaSale;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2015-12-16.
 */
public class MediaResfileDb {

    /*
    从productId列表中过滤支持某个设备
    参数：
        productId，商品id列表
        environment 设备
     */
    public static List<MediaResfile> getMediaByDevice(List<String> productId, TestDevice device) throws Exception {
        String selectSQL = "select * from  media_resfile  where media_id in ("+ StringUtils.join(productId,",")+") and DEVICE_TYPE_CODE='"+device.toString()+"'";
        List<MediaResfile> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaResfile.class);
        return infos;
    }
}
