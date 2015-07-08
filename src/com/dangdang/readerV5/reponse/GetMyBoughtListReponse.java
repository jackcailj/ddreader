package com.dangdang.readerV5.reponse;

import com.dangdang.digital.MediaBought;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-8.
 */
public class GetMyBoughtListReponse {
    List<MediaBought> boughtList;

    public List<MediaBought> getBoughtList() {
        return boughtList;
    }

    public void setBoughtList(List<MediaBought> boughtList) {
        this.boughtList = boughtList;
    }
}
