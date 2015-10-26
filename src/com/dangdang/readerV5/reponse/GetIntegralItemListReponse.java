package com.dangdang.readerV5.reponse;

import com.dangdang.account.meta.AccountIntegralItems;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-5.
 */
public class GetIntegralItemListReponse {
    List<AccountIntegralItems> accountIntegralList;

    public List<AccountIntegralItems> getAccountIntegralList() {
        return accountIntegralList;
    }

    public void setAccountIntegralList(List<AccountIntegralItems> accountIntegralList) {
        this.accountIntegralList = accountIntegralList;
    }
}
