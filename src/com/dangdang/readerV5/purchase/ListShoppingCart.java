package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ShoppingCartDb;
import com.dangdang.digital.meta.ShoppingCart;
import com.dangdang.readerV5.reponse.ListShoppingCartReponse;
import com.dangdang.readerV5.reponse.Products;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class ListShoppingCart extends FixtureBase{

    ReponseV2<ListShoppingCartReponse> reponseResult;


    public  ListShoppingCart(){addAction("listShoppingCart");}

    public  ListShoppingCart(ILogin login){
        setLogin(login);
        if(login!=null) {
            paramMap.put("token", login.getToken());
        }
        addAction("listShoppingCart");
    }

    public  ListShoppingCart(String cartId){

        paramMap.put("cartId", cartId);

        addAction("listShoppingCart");
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ListShoppingCartReponse>>(){});
    }

    public ReponseV2<ListShoppingCartReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            if(login!=null){
                ShoppingCart shoppingCart =ShoppingCartDb.getShoppingCart(login.getCustId());
                List<Products> productses = DbUtil.selectList(Config.YCDBConfig, "select scd.cart_id as cartId,m.author_name as authorPenname,m.cover_pic as coverPic,m.creation_date as creationDate,m.media_id as mediaId,m.price as price,scd.qutity,scd.sale_id as saleId,m.title from shopping_cart_detail scd\n" +
                        "left join media m on  scd.sale_id=m.sale_id\n" +
                        "where m.media_id is not null and scd.cart_id=" +shoppingCart.getCartId() +" order by scd.creation_date desc", Products.class);

                dataVerifyManager.add(new ValueVerify<String>(shoppingCart.getCartId(), reponseResult.getData().getCartId()).setVerifyContent("验证返回的cartId正确"));
                dataVerifyManager.add(new ListVerify(productses, reponseResult.getData().getProducts(), true).setVerifyContent("验证购物车列表数据一致"));
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<String>(null, reponseResult.getData().getCartId()).setVerifyContent("参数不正确，验证返回的不返回CartId"),VerifyResult.SUCCESS);
            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getProducts()).setVerifyContent("参数不正确，验证返回的不返回products"),VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
