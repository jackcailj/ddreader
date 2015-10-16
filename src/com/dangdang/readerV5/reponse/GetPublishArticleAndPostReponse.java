package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaDigest;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class GetPublishArticleAndPostReponse {
    List<PublistPostInfo> publishList;

    public List<PublistPostInfo> getPublishList() {
        return publishList;
    }

    public void setPublishList(List<PublistPostInfo> publishList) {
        this.publishList = publishList;
    }
}
