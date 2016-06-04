package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.ShoppingCartDb;
import com.dangdang.db.digital.ShoppingCartDetailDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.readerV5.reponse.ListShoppingCartReponse;
import com.dangdang.readerV5.reponse.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class ListShoppingCart extends FixtureBase{
    ReponseV2<ListShoppingCartReponse> reponseResult;
    String userName = "";
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
    
    public String getReponseResult(){
    	return result.toString();
    }

    @Override
    public void doWork() throws Exception {
    	userName= paramMap.get("userName");
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ListShoppingCartReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){   
        	String cartId;
        	String cartIdFromParam = paramMap.get("cartId");
        	if(login==null){
        		if(!"".equals(cartIdFromParam)){
        			dataVerifyManager.add(new ValueVerify<String>(cartIdFromParam, reponseResult.getData().getCartId()).setVerifyContent("验证cartId！"));
        		}else
        			dataVerifyManager.add(new ValueVerify<String>(null, reponseResult.getData().getCartId()).setVerifyContent("验证cartId！"), VerifyResult.FAILED);
        	}else{
        		//String custId = UserDeviceDb.getCustIdByName(userName);
        		cartId = ShoppingCartDb.getShoppingCart(login.getCustId()).getCartId();      	
        		List<Products> productsFromDB = ShoppingCartDetailDb.getShoppingCartList(cartId);
        		List<Products> productsFromJson = reponseResult.getData().getProducts();
        		List<String> mediaIdsFromDB = new ArrayList<String>(); 
        		for(int i=0; i<productsFromDB.size(); i++){
        			mediaIdsFromDB.add(productsFromDB.get(i).getMediaId());
        		}
        		dataVerifyManager.add(new ValueVerify<String>(cartId, reponseResult.getData().getCartId()).setVerifyContent("验证cartId！"));
        		dataVerifyManager.add(new ValueVerify<Integer>(productsFromDB.size(), productsFromJson.size()).setVerifyContent("验证购物车列表size是否一致！"));
        		if(productsFromDB.size()==productsFromJson.size()){
        			for(int j=0; j<productsFromJson.size(); j++){
        				String mediaTmp = productsFromJson.get(j).getMediaId();
        				dataVerifyManager.add(new ExpressionVerify(mediaIdsFromDB.contains(mediaTmp)).setVerifyContent("验证购物车列表是否包含" + mediaTmp));
        			}
        		}
        	}
        }
//            if(login!=null){
//                ShoppingCart shoppingCart =ShoppingCartDb.getShoppingCart(login.getCustId());
//                List<Products> productses = DbUtil.selectList(Config.YCDBConfig, "select scd.cart_id as cartId,m.author_name as authorPenname,m.cover_pic as coverPic,m.creation_date as creationDate,m.media_id as mediaId,m.price as price,scd.qutity,scd.sale_id as saleId,m.title from shopping_cart_detail scd\n" +
//                        "left join media m on  scd.sale_id=m.sale_id\n" +
//                        "where m.media_id is not null and scd.cart_id=" +shoppingCart.getCartId() +" order by scd.creation_date desc", Products.class);
//
//                dataVerifyManager.add(new ValueVerify<String>(shoppingCart.getCartId(), reponseResult.getData().getCartId()).setVerifyContent("验证返回的cartId正确"));
//                dataVerifyManager.add(new ListVerify(productses, reponseResult.getData().getProducts(), true).setVerifyContent("验证购物车列表数据一致"));
//            }
//        }
//        else{
//            dataVerifyManager.add(new ValueVerify<String>(null, reponseResult.getData().getCartId()).setVerifyContent("参数不正确，验证返回的不返回CartId"),VerifyResult.SUCCESS);
//            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getProducts()).setVerifyContent("参数不正确，验证返回的不返回products"),VerifyResult.SUCCESS);


        super.dataVerify();
        
    }
}
