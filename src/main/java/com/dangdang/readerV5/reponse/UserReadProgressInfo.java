package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_plan.UserReadProgressTrainingInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-21.
 */
public class UserReadProgressInfo {
    String custId;
    String desc;
    Double finishReadRate;
    String imgUrl;
    String name;
    Long planId;
    Long planRemainDay;
    Integer status;

    List<UserReadProgressTrainingInfo> trainingReadProgress;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getFinishReadRate() {
        return finishReadRate;
    }

    public void setFinishReadRate(Double finishReadRate) {
        this.finishReadRate = finishReadRate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanRemainDay() {
        return planRemainDay;
    }

    public void setPlanRemainDay(Long planRemainDay) {
        this.planRemainDay = planRemainDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UserReadProgressTrainingInfo> getTrainingReadProgress() {
        return trainingReadProgress;
    }

    public void setTrainingReadProgress(List<UserReadProgressTrainingInfo> trainingReadProgress) {
        this.trainingReadProgress = trainingReadProgress;
    }
}
