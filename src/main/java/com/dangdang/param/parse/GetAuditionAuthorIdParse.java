package com.dangdang.param.parse;

import com.dangdang.bookbar.meta.AuditionAuthor;
import com.dangdang.db.bookbar.AuditionAuthorDb;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class GetAuditionAuthorIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        List<AuditionAuthor> auditionAuthors= AuditionAuthorDb.getAuditionAuthorList();

        Random random = new Random();
        Integer index=random.nextInt(auditionAuthors.size()-1);


        paramMap.put(key,auditionAuthors.get(index).getAuthorId().toString());
    }
}
