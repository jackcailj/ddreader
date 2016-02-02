package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class RecommendBookFriendListReponse {
    List<BookFriendInfo> recommendBookFriends;

    public List<BookFriendInfo> getRecommendBookFriends() {
        return recommendBookFriends;
    }

    public void setRecommendBookFriends(List<BookFriendInfo> recommendBookFriends) {
        this.recommendBookFriends = recommendBookFriends;
    }
}
