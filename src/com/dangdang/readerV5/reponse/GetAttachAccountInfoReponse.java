package com.dangdang.readerV5.reponse;

import com.dangdang.account.meta.AttachAccountItems;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-16.
 */
public class GetAttachAccountInfoReponse {
    Long accountTotal;
    List<AttachAccountItems> result;

    public Long getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }

    public List<AttachAccountItems> getResult() {
        return result;
    }

    public void setResult(List<AttachAccountItems> result) {
        this.result = result;
    }
}
