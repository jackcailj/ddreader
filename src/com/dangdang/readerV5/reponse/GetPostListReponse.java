package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-17.
 */
public class GetPostListReponse {
    List<PostInfo> postList;

    public List<PostInfo> getPostList() {
        return postList;
    }

    public void setPostList(List<PostInfo> postList) {
        this.postList = postList;
    }
}
