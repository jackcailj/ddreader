package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaDigest;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class GetPublishArticleAndPostReponse {
    List<MediaDigest> publishList;

    public List<MediaDigest> getPublishList() {
        return publishList;
    }

    public void setPublishList(List<MediaDigest> publishList) {
        this.publishList = publishList;
    }
}
