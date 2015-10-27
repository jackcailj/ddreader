package com.dangdang.param.parse;

import com.dangdang.config.Config;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;

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

        Media media= MediaDb.getMedia(BookType.YUANCHUANG, BookStatus.VALID);
        paramMap.put(key,media.getMediaId().toString());
    }
}
