package com.dangdang.reader.functional.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.reader.functional.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.bookshelf.GetUserBookList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-16.
 */
public class GetBookShelfProductIdParse implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        int number=1;
        if(StringUtils.isNotBlank(param)){
            number=Integer.parseInt(param);
        }

        GetUserBookList getUserBookList = new GetUserBookList((ILogin) VariableStore.get(VarKey.LOGIN));
        getUserBookList.doWork();

        List<String> productIds=new ArrayList<String>();
        for(int i=0;i<number;i++){
            productIds.add(getUserBookList.getReponseResult().getData().getMediaList().get(i).getMediaId().toString());
        }

        paramMap.put(key,StringUtils.join(productIds,","));
    }
}
