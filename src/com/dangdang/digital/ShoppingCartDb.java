package com.dangdang.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.ShoppingCart;
import com.dangdang.digital.meta.ShoppingCartDetail;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class ShoppingCartDb {


    /*
    根据custid获取shoppingCart
     */
    public static ShoppingCart getShoppingCart(String custId) throws Exception {
        String selectString="select * from shopping_cart where cust_id="+custId;
        ShoppingCart shoppingCart = DbUtil.selectOne(Config.YCDBConfig,selectString,ShoppingCart.class);

        return shoppingCart;
    }

    /*
   根据custid获取shoppingCart
    */
    public static List<ShoppingCartDetail> getShoppingCartDetail(String custId) throws Exception {
        String selectString="select product_id from shopping_cart_detail sd left join shopping_cart  sc on sd.cart_id=sc.cart_id where sc.cust_id="+custId;
        List<ShoppingCartDetail> details = DbUtil.selectList(Config.YCDBConfig, selectString,ShoppingCartDetail.class);

        return details;
    }
}
