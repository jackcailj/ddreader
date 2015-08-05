package com.dangdang.readerV5.reponse;

import com.dangdang.account.IntegralItem;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-5.
 */
public class GetIntegralItemListReponse {
    List<IntegralItem> accountIntegralList;

    public List<IntegralItem> getAccountIntegralList() {
        return accountIntegralList;
    }

    public void setAccountIntegralList(List<IntegralItem> accountIntegralList) {
        this.accountIntegralList = accountIntegralList;
    }
}
