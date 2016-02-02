package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-10-13.
 */
public class GetMediaGiveReponse {
    Integer amount;
    List<Media> list;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Media> getList() {
        return list;
    }

    public void setList(List<Media> list) {
        this.list = list;
    }
}
