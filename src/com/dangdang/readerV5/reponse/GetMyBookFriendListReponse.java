package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-23.
 */
public class GetMyBookFriendListReponse {
    List<BookFriendInfo> bookFriendList;
    Boolean hasNext;

    public List<BookFriendInfo> getBookFriendList() {
        return bookFriendList;
    }

    public void setBookFriendList(List<BookFriendInfo> bookFriendList) {
        this.bookFriendList = bookFriendList;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
