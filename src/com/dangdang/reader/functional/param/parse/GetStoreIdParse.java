package com.dangdang.reader.functional.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.reader.functional.param.parse.IParamParse;
import com.dangdang.reader.functional.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.DDReaderStoreUpList;
import com.dangdang.digital.StoreUpType;
import com.dangdang.readerV5.reponse.DDReaderStoreUpArticle;
import com.dangdang.readerV5.reponse.DDReaderStoreUpMedia;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetStoreIdParse implements IParamParse{


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
        if(StringUtils.isNotBlank(param)){

            StoreUpType storeUpType = StoreUpType.valueOf(paramMap.get("type").toUpperCase());

            Integer number=Integer.parseInt(param);

            DDReaderStoreUpList storeUpList = new DDReaderStoreUpList((ILogin) VariableStore.get(VarKey.LOGIN),storeUpType );
            storeUpList.doWork();

            List<String> productIds = new ArrayList<String>();


            List mediaIds = new ArrayList();
            if(storeUpType==StoreUpType.MEDIA) {
                for (DDReaderStoreUpMedia products : storeUpList.getReponseResultMedia().getData().getStoreUpList()) {
                    productIds.add("" + products.getProductId());
                    if(productIds.size()==number){
                        break;
                    }

                }

            }
            else {

                for (DDReaderStoreUpArticle products : storeUpList.getReponseResultArticle().getData().getStoreUpList()) {
                    productIds.add("" + products.getArticleId());
                    if(productIds.size()==number){
                        break;
                    }
                }
            }

            paramMap.put(key, StringUtils.join(productIds,","));

        }
        else{
            throw new Exception("GetProductAndSaleIdParse参数为空");
        }

    }
}
