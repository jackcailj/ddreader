package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_activitiy.PlanActivityVo;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-29.
 */
public class MyActivityListReponse {
    List<PlanActivityVo> activityList;

    public List<PlanActivityVo> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<PlanActivityVo> activityList) {
        this.activityList = activityList;
    }
}
