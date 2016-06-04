package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-21.
 */
public class GetUserMultiReadProgressReponse {

    List<UserReadProgressInfo> multiReadProgressInfos;

    public List<UserReadProgressInfo> getMultiReadProgressInfos() {
        return multiReadProgressInfos;
    }

    public void setMultiReadProgressInfos(List<UserReadProgressInfo> multiReadProgressInfos) {
        this.multiReadProgressInfos = multiReadProgressInfos;
    }
}
