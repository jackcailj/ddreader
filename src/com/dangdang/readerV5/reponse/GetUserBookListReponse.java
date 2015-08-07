package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class GetUserBookListReponse {
    List<UserBookMedia> mediaList;

    public List<UserBookMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<UserBookMedia> mediaList) {
        this.mediaList = mediaList;
    }
}
