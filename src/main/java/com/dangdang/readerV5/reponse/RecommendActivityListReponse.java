package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-28.
 */
public class RecommendActivityListReponse {

    List<PlanActivityInfo> activityList;

    public List<PlanActivityInfo> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<PlanActivityInfo> activityList) {
        this.activityList = activityList;
    }
}
