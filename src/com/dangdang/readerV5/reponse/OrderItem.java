package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2015-10-15.
 */
public class OrderItem {
    String barginPrice;
    String orderQuantity;
    String productId;
    String productName;

    public String getBarginPrice() {
        return barginPrice;
    }

    public void setBarginPrice(String barginPrice) {
        this.barginPrice = barginPrice;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
