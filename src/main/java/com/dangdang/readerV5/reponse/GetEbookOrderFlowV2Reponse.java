package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetEbookOrderFlowV2Reponse {
    String freeProductIdList;
    String key;
    Integer payable;
    List<PayingProduct> payingProducts;

    public String getFreeProductIdList() {
        return freeProductIdList;
    }

    public void setFreeProductIdList(String freeProductIdList) {
        this.freeProductIdList = freeProductIdList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPayable() {
        return payable;
    }

    public void setPayable(Integer payable) {
        this.payable = payable;
    }

    public List<PayingProduct> getPayingProducts() {
        return payingProducts;
    }

    public void setPayingProducts(List<PayingProduct> payingProducts) {
        this.payingProducts = payingProducts;
    }
}
