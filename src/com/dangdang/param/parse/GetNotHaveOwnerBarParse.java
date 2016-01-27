package com.dangdang.param.parse;

import com.dangdang.bookbar.meta.Bar;
import com.dangdang.db.bookbar.BarDb;

import java.util.Map;

/**
 * Created by cailianjie on 2016-1-21.
 */
public class GetNotHaveOwnerBarParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        Bar notHaveOwnerBar= BarDb.getNotHaveOwnerBar();

        paramMap.put(key,notHaveOwnerBar.getBarId().toString());
    }
}
