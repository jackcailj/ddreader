package com.dangdang.param.parse;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;

import java.util.Map;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class GetMediaParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        String selectString="select * from media where doc_type='ebook' and shelf_status =1 limit 1";

        Media media = DbUtil.selectOne(Config.YCDBConfig,selectString,Media.class);
        paramMap.put(key,media.getMediaId().toString());
    }
}
