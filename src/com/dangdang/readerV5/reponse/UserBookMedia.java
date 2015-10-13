package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class UserBookMedia extends Media{
    short isHide;
    String relationType;
    Integer authorityType;

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

    public Integer getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(Integer authorityType) {
        this.authorityType = authorityType;
    }
}
