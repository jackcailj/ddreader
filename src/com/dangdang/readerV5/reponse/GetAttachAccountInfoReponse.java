package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-16.
 */
public class GetAttachAccountInfoReponse {
    Long accountTotal;
    List<AttachAccountItem> result;

    public Long getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }

    public List<AttachAccountItem> getResult() {
        return result;
    }

    public void setResult(List<AttachAccountItem> result) {
        this.result = result;
    }
}
