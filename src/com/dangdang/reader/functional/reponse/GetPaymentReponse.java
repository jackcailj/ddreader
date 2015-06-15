package com.dangdang.reader.functional.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class GetPaymentReponse {


    List<PayMent> payment;

    public List<PayMent> getPayment() {
        return payment;
    }

    public void setPayment(List<PayMent> payment) {
        this.payment = payment;
    }
}
