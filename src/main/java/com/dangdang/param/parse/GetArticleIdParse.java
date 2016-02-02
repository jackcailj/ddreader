package com.dangdang.param.parse;

import com.dangdang.db.digital.MediaDigestDb;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by cailianjie on 2016-1-22.
 */
public class GetArticleIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        List<String> artiles= MediaDigestDb.getDigestId("3");

        paramMap.put(key, artiles.get(new Random().nextInt(artiles.size())));
    }
}
