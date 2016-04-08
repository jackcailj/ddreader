package com.dangdang.readerV5.reponse;


import com.dangdang.digital.meta.MediaDigest;

/**
 * Created by cailianjie on 2016-3-24.
 */
public class DigestInfo {
    Channel channel;

    HomePageDigestInfo digest;


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public HomePageDigestInfo getDigest() {
        return digest;
    }

    public void setDigest(HomePageDigestInfo digest) {
        this.digest = digest;
    }
}
