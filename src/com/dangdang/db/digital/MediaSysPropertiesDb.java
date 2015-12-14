package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.digital.meta.MediaSysProperties;
import com.dangdang.enumeration.SysPropertiesEnum;

/**
 * Created by cailianjie on 2015-12-8.
 */
public class MediaSysPropertiesDb {

    public static String getSysProperties(SysPropertiesEnum key) throws Exception {
        String selectString="select * from media_sys_properties where key_name='"+key.toString()+"'";
        MediaSysProperties properties= DbUtil.selectOne(Config.YCDBConfig, selectString, MediaSysProperties.class);
        return properties.getKeyValue();
    }

}
