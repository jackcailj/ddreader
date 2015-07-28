package com.dangdang.readerV5.personal_center.bookfriend;

import com.dangdang.readerV5.reponse.Book;

/**
 * Created by cailianjie on 2015-7-27.
 */
public enum BookFriendRelation {
    NO_RELATION(-1),
    ACTIVE(0),//主动关注
    PASSIVE(1),//被动关注
    EACHOTHER(2);//互相关注

    int content;
    boolean isActive;
    BookFriendRelation(int value){
        content=value;
    }

    public void setIsActive(boolean bActive){
        isActive=bActive;
    }

    public boolean isActive(){
        return isActive;
    }


    public static BookFriendRelation valueOf(int value){
        switch (value) {
            case -1:
                return NO_RELATION;
            case 0:
                return ACTIVE;
            case 1:
                return PASSIVE;
            case 2:
                return EACHOTHER;
            default:
                return null;
        }
    }
}
