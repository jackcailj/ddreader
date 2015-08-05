package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;

/**
 * Created by cailianjie on 2015-8-3.
 */
public class UserBookMedia extends Media{
    short isHide;

    public short getIsHide() {
        return isHide;
    }

    public void setIsHide(short isHide) {
        this.isHide = isHide;
    }
}
