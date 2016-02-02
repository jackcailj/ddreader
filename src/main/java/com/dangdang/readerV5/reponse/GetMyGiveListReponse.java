package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-9.
 */
public class GetMyGiveListReponse {
    Integer amount;
    List<GetMyGiveListData> saleList;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<GetMyGiveListData> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<GetMyGiveListData> saleList) {
        this.saleList = saleList;
    }
}
