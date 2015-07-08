package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaBoughtDetail;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-8.
 */
public class GetMyBoughtDetailListReponse {

    List<MediaBoughtDetail> boughtDetailList;

    public List<MediaBoughtDetail> getBoughtDetailList() {
        return boughtDetailList;
    }

    public void setBoughtDetailList(List<MediaBoughtDetail> boughtDetailList) {
        this.boughtDetailList = boughtDetailList;
    }
}
