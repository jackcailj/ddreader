package com.dangdang.param.parse;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.db.digital.BookStatus;
import com.dangdang.db.digital.BookType;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.param.parse._enum.VarKey;
import com.dangdang.readerV5.purchase.ListShoppingCart;
import com.dangdang.readerV5.reponse.Products;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetProductAndSaleIdParse implements IParamParse{


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
            String[] params = ParamParse.parseParam(param);
            BookType bookType = BookType.valueOf(params[0]);
            BookStatus bookStatus= BookStatus.valueOf(params[1]);

            //用来处理混合情况，比如有效和无效的productId一起
            String[] numberSplit =ParamParse.parseParam(params[2], ParamParse.AND);
            Integer number=Integer.parseInt(numberSplit[0].trim());

            //获取购物车中的商品
            ListShoppingCart listShoppingCart =new ListShoppingCart((ILogin) VariableStore.get(VarKey.LOGIN));
            listShoppingCart.doWork();
            List<String> productIds = new ArrayList<String>();

            for (Products products : listShoppingCart.getReponseResult().getData().getProducts()) {
                    productIds.add(products.getMediaId());
            }

            //查找不在购物车中的prouctid，用来往购物车中添加
            List<Media> medias=MediaDb.getMedias(bookType, bookStatus, number,productIds);
            List mediaIds = new ArrayList();
            for(Media media : medias){
                Map<String,String> mediaMap=new HashMap<String, String>();
                mediaMap.put("productId",media.getMediaId().toString());
                mediaMap.put("saleId", media.getSaleId().toString());
                mediaIds.add(mediaMap);
            }


            paramMap.put(key, JSONObject.toJSONString(mediaIds));
            VariableStore.add(VarKey.MEDIAS,medias);

        }
        else{
            throw new Exception("GetProductAndSaleIdParse参数为空");
        }

    }
}
