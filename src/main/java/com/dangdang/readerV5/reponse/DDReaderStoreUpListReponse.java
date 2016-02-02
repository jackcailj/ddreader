package com.dangdang.readerV5.reponse;

import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-31.
 */
public class DDReaderStoreUpListReponse<T> {
    List<T> storeUpList;

    public List<T> getStoreUpList() {
        return storeUpList;
    }

    public void setStoreUpList(List<T> storeUpList) {
        this.storeUpList = storeUpList;
    }
}
