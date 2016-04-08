package com.dangdang.param.parse;

import com.dangdang.db.digital.MediaChapterDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaChapter;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.enumeration.GetChapterEnum;

import java.util.Map;

/**
 * Created by cailianjie on 2015-12-10.
 */
public class GetChapterIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        GetChapterEnum status = GetChapterEnum.valueOf(param);

        //Media media = MediaDb.getMedia(BookType.YUANCHUANG, BookStatus.VALID);
        MediaChapter mediaChapter = MediaChapterDb.getBookChapter(status);

        paramMap.put(key,mediaChapter.getId().toString());
    }
}
