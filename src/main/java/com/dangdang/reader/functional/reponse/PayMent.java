package com.dangdang.reader.functional.reponse;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class PayMent {

    Integer maxGiving;
    Integer paymentId;
    String paymentName;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getMaxGiving() {
        return maxGiving;
    }

    public void setMaxGiving(Integer maxGiving) {
        this.maxGiving = maxGiving;
    }
}
