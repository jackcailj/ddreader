package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-16.
 */
public class PurchaseEbookVirtualPaymentReponse {
    Integer costOfConsume;
    Integer costOfSaveBought;
    Integer costOfSaveOrder;
    Integer costOfbeforeOrderForm;
    List<MobileMediaVo> mobileMediaVoList;

    List<String> productIds;


    public Integer getCostOfConsume() {
        return costOfConsume;
    }

    public void setCostOfConsume(Integer costOfConsume) {
        this.costOfConsume = costOfConsume;
    }

    public Integer getCostOfSaveBought() {
        return costOfSaveBought;
    }

    public void setCostOfSaveBought(Integer costOfSaveBought) {
        this.costOfSaveBought = costOfSaveBought;
    }

    public Integer getCostOfSaveOrder() {
        return costOfSaveOrder;
    }

    public void setCostOfSaveOrder(Integer costOfSaveOrder) {
        this.costOfSaveOrder = costOfSaveOrder;
    }

    public Integer getCostOfbeforeOrderForm() {
        return costOfbeforeOrderForm;
    }

    public void setCostOfbeforeOrderForm(Integer costOfbeforeOrderForm) {
        this.costOfbeforeOrderForm = costOfbeforeOrderForm;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public List<MobileMediaVo> getMobileMediaVoList() {
        return mobileMediaVoList;
    }

    public void setMobileMediaVoList(List<MobileMediaVo> mobileMediaVoList) {
        this.mobileMediaVoList = mobileMediaVoList;
    }
}
