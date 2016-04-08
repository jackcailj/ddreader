package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBlock;
import com.dangdang.digital.meta.MediaBooklist;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-30.
 */
public class MediaBlockDb {

    /*
        通过code获取block
     */
    public  static MediaBlock getBlock(String code) throws Exception {
        String selectSQL = "SELECT * from media_block where code='"+code+"' and status =1";
        MediaBlock infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL, MediaBlock.class);
        return infos;
    }
}
