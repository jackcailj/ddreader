package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-29.
 */
public class QueryBookReviewReponse {
    List<HomePagePostInfos> BookReviewList;

    public List<HomePagePostInfos> getBookReviewList() {
        return BookReviewList;
    }

    public void setBookReviewList(List<HomePagePostInfos> bookReviewList) {
        BookReviewList = bookReviewList;
    }
}
