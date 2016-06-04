package com.dangdang.readerV5.read_plan;

import com.dangdang.ucenter.vo.UserBaseInfo;

/**
 * Created by cailianjie on 2016-5-31.
 */
public class ReadTrainingTopInfo {
    String custId;
    Long lastModifyTime;
    Long mediaId;
    Long processId;
    Double todayFinishRate;
    Long trainingId;
    UserBaseInfo userBaseInfo;
    Double weekFinishRate;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Double getTodayFinishRate() {
        return todayFinishRate;
    }

    public void setTodayFinishRate(Double todayFinishRate) {
        this.todayFinishRate = todayFinishRate;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }

    public Double getWeekFinishRate() {
        return weekFinishRate;
    }

    public void setWeekFinishRate(Double weekFinishRate) {
        this.weekFinishRate = weekFinishRate;
    }
}
