package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.*;
import com.dangdang.digital.meta.Media;

import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class MediaByCustIdInfo {
    Long custId;
    Date getDate;
    String nickName;

    List<MediaInfoPacket> mediaList;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Date getGetDate() {
        return getDate;
    }

    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<MediaInfoPacket> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaInfoPacket> mediaList) {
        this.mediaList = mediaList;
    }
}
