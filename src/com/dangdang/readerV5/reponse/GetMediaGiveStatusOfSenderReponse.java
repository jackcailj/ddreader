package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class GetMediaGiveStatusOfSenderReponse {
    String advice;

    List<MediaByCustIdInfo> mediaList;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public List<MediaByCustIdInfo> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaByCustIdInfo> mediaList) {
        this.mediaList = mediaList;
    }
}
