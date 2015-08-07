package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaActivityInfo;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-31.
 */
public class GetDepositShowViewReponse {
    List<MediaActivityInfo> activityInfos;

    public List<MediaActivityInfo> getActivityInfos() {
        return activityInfos;
    }

    public void setActivityInfos(List<MediaActivityInfo> activityInfos) {
        this.activityInfos = activityInfos;
    }
}
