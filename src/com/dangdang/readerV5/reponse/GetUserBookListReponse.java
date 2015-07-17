package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class GetUserBookListReponse {
    List<Media> mediaList;

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }
}
