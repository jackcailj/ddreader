package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-10-15.
 */
public class OrderListInfo {
    Integer errorCode;
    List<OrderInfo> orders;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }
}
