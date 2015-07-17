package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class ListShoppingCartReponse {
    String cartId;
    List<Products> products;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
