package com.dangdang.reader.functional.param.parse;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.*;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaCustomerSubscription;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.reader.functional.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.DDReaderStoreUpList;
import com.dangdang.readerV5.reponse.DDReaderStoreUpArticle;
import com.dangdang.readerV5.reponse.DDReaderStoreUpMedia;
import com.dangdang.readerV5.reponse.DDReaderStoreUpPost;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetSubscribeParse implements IParamParse{


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



            //用来处理混合情况，比如有效和无效的productId一起
        if(param!=null) {
            String[] numberSplit = ParamParse.parseParam(param,ParamParse.AND);
            Integer number = Integer.parseInt(numberSplit[0].trim());

            List<MediaCustomerSubscription> subscriptions = CustomerSubscribeDb.getCustomerSubscription(((ILogin) VariableStore.get(VarKey.LOGIN)).getCustId(), 1);

            List<String> productIds = new ArrayList<String>();

            for (MediaCustomerSubscription products : subscriptions) {
                productIds.add("" + products.getMediaId());
                if(productIds.size()==number){
                    break;
                }
            }

            if (numberSplit.length > 1) {
                productIds.add(numberSplit[1].trim());
            }

            paramMap.put(key, StringUtils.join(productIds, "_"));
        }else{
            throw new Exception("param不能为空");
        }

    }
}
