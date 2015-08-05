package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.*;
import com.dangdang.digital.meta.Channel;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-1.
 */
public class MyChannelListReponse {
    List<Channel> channelList;

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}
