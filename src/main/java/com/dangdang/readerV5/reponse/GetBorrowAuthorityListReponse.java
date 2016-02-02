package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class GetBorrowAuthorityListReponse {
    List<Media> borrowList;

    public List<Media> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<Media> borrowList) {
        this.borrowList = borrowList;
    }
}
