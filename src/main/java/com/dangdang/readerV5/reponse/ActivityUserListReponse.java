package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class ActivityUserListReponse {

    List<JoinActivityUserInfo> userList;

    public List<JoinActivityUserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<JoinActivityUserInfo> userList) {
        this.userList = userList;
    }
}
