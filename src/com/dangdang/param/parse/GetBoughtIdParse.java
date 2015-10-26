package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.meta.MediaBought;
import com.dangdang.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.GetMyBoughtList;

import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetBoughtIdParse implements IParamParse{


    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }


    /*
    获取产品productid
    参数：
        param：用，分割多个参数，格式BookType,ProductIdsEnum,数量
     */
    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {


        GetMyBoughtList boughtList = new GetMyBoughtList((ILogin) VariableStore.get(VarKey.LOGIN),1);
        boughtList.doWork();
        MediaBought mediaBought =boughtList.getReponseResult().getData().getBoughtList().get(0);
        paramMap.put(key, mediaBought.getBoughtId().toString());



    }
}
