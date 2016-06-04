package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2016-4-27.
 */
public class JoinActivityUserInfo {
    Long activityId;
    Long custId;
    Long joinActivityTime;

    UserBaseInfo userBaseInfo;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getJoinActivityTime() {
        return joinActivityTime;
    }

    public void setJoinActivityTime(Long joinActivityTime) {
        this.joinActivityTime = joinActivityTime;
    }

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }
}
