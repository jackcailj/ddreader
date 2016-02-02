package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class UserBookMedia {

    Long mediaId;
    Long saleId;
    short isHide;
    String relationType;
    short authorityType;

    public short getIsHide() {
        return isHide;
    }

    public void setIsHide(short isHide) {
        this.isHide = isHide;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public short getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(short authorityType) {
        this.authorityType = authorityType;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }
}
