package com.dangdang.db.digital;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.ShoppingCart;
import com.dangdang.readerV5.reponse.Products;

/**
 * 
 * @author guohaiying
 * @date 2016年6月3日 下午4:17:08
 */
public class ShoppingCartDetailDb {
	
    public static List<Products> getShoppingCartList(String cartId) throws Exception {
        String selectSQL="select scd.cart_id as cartId," +
        		" m.author_name as authorPenname," +
        		" m.cover_pic as coverPic," +
        		" m.creation_date as creationDate," +
        		" m.media_id as mediaId," +
        		" m.price as price," +
        		" scd.qutity," +
        		" scd.sale_id as saleId," +
        		" m.title " +
        		" from shopping_cart_detail scd left join media m on scd.sale_id=m.sale_id " +
        		" where m.media_id is not null and scd.cart_id="+cartId +
        		" order by scd.creation_date desc;";
        List<Products> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Products.class);
        return infos;
    }
    
    public static void main(String[] args) throws Exception{
    	List<Products> list = ShoppingCartDetailDb.getShoppingCartList("1510131423293841");
    	System.out.println("list: "+ JSONArray.toJSONString(list));
    }

}
