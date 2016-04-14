package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.reponse.ReponseV2;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-24.
 */
public class GetAttentionListReponse {

    List<AttentionInfo> bookFriendMoments;

    public List<AttentionInfo> getBookFriendMoments() {
        return bookFriendMoments;
    }

    public void setBookFriendMoments(List<AttentionInfo> bookFriendMoments) {
        this.bookFriendMoments = bookFriendMoments;
    }
}
