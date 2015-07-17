package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.ShoppingCartDetail;
import com.dangdang.readerV5.reponse.DeleteShoppingCartReponse;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class DeleteShoppingCart extends FixtureBase{



    ReponseV2<DeleteShoppingCartReponse> reponseResult;

    public ReponseV2<DeleteShoppingCartReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<DeleteShoppingCartReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<String>("success", reponseResult.getData().getResult()));

            ListShoppingCart listShoppingCart= new ListShoppingCart(paramMap.get("cartId"));
            listShoppingCart.doWork();

            String productArray = paramMap.get("productIds");
            String[] productIds=paramMap.get("productIds").split(",");
            //List addProducts = JSONObject.parseArray(productArray);
            String regex="";
            for(String product : productIds){
                //Map<String,String> productMap=(Map<String,String>)product;
                regex="\"mediaId\"\\s*:\\s*[\"]*"+product+"\\s*[,\"}]*";

                dataVerifyManager.add(new RegexVerify(regex,JSONObject.toJSONString(listShoppingCart.getReponseResult())).setVerifyContent("验证购物车列表中不存在删除的商品（productId="+product+"）"), VerifyResult.FAILED);
            }


        }
        else{
            dataVerifyManager.add(new ValueVerify<String>(null,reponseResult.getData().getResult()));
        }
        super.dataVerify();
    }
}
