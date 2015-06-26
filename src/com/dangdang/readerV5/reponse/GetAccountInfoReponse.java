package com.dangdang.readerV5.reponse;

import com.dangdang.account.meta.AccountConsumeItems;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-25.
 */
public class GetAccountInfoReponse {
    List<AccountConsumeItems> result;

    public List<AccountConsumeItems> getResult() {
        return result;
    }

    public void setResult(List<AccountConsumeItems> result) {
        this.result = result;
    }
}
