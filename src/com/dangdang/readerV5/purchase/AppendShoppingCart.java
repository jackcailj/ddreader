package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.digital.ShoppingCartDb;
import com.dangdang.digital.meta.ShoppingCart;
import com.dangdang.digital.meta.ShoppingCartDetail;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class AppendShoppingCart extends FixtureBase{

    public AppendShoppingCart(){addAction("appendShoppingCart");}


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            String cartId=paramMap.get("cartId");

            if(StringUtils.isNotBlank(paramMap.get("test_flag")) && !paramMap.get("test_flag").equals("xiajia")){
                cartId=paramMap.get("test_flag");
            }
            ListShoppingCart listShoppingCart = new ListShoppingCart(cartId);
            listShoppingCart.doWorkAndVerify();

            String productArray = paramMap.get("productArray");
            List addProducts = JSONObject.parseArray(productArray);
            String regex="";
            for(Object product : addProducts){
                Map<String,String> productMap=(Map<String,String>)product;
                regex="\"mediaId\"\\s*:\\s*"+productMap.get("productId")+"\\s*";

                dataVerifyManager.add(new RegexVerify(regex, listShoppingCart.getResult().toString()).setVerifyContent("验证商品添加到购物车中productId="+productMap.get("productId")),paramMap.get("test_flag").equals("xiajia")?
                        VerifyResult.FAILED:VerifyResult.SUCCESS);
            }

        }

        super.dataVerify();
    }
}
