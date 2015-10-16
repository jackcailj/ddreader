package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaDigest;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetPostListReponse {
    List<PostListDigestInfo> postList;

    public List<PostListDigestInfo> getPostList() {
        return postList;
    }

    public void setPostList(List<PostListDigestInfo> postList) {
        this.postList = postList;
    }
}
