package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaDigest;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetPostListReponse {
    List<MediaDigest> postList;

    public List<MediaDigest> getPostList() {
        return postList;
    }

    public void setPostList(List<MediaDigest> postList) {
        this.postList = postList;
    }
}
