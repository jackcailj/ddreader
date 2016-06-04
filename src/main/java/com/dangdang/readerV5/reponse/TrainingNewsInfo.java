package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class TrainingNewsInfo {

    Long custId;
    Long mediaId;
    Long processId;
    Double todayFinishRate;
    Long trainingId;

    UserBaseInfo userBaseInfo;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
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
}
