package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.VarKey;
import com.dangdang.readerV5.personal_center.bookshelf.GetUserBookList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-16.
 *获取购买的书籍Id，参数为数量
 * 调用方式为#GetBookShelfProductId#UNHIDDEN或者HIDDEN,数量
 *
 */
public class GetBookShelfProductIdParse implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        int number=1;

        String[] splitString = ParamParse.parseParam(param);



        number=Integer.parseInt(splitString[1]);


        GetUserBookList getUserBookList = new GetUserBookList((ILogin) VariableStore.get(VarKey.LOGIN));
        getUserBookList.doWork();

        List<String> productIds=new ArrayList<String>();
        for(int i=0;i<getUserBookList.getReponseResult().getData().getMediaList().size();i++){
            if(splitString[0].toLowerCase().equals("hidden") && getUserBookList.getReponseResult().getData().getMediaList().get(i).getIsHide()==1){
                productIds.add(getUserBookList.getReponseResult().getData().getMediaList().get(i).getMediaId().toString());
            }
            else if(splitString[0].toLowerCase().equals("unhidden") && getUserBookList.getReponseResult().getData().getMediaList().get(i).getIsHide()==0){
                productIds.add(getUserBookList.getReponseResult().getData().getMediaList().get(i).getMediaId().toString());
            }

            if(productIds.size()==number){
                break;
            }

        }

        paramMap.put(key,StringUtils.join(productIds,","));
    }
}
