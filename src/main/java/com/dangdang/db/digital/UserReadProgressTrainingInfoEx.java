package com.dangdang.db.digital;

import com.dangdang.readerV5.read_plan.UserReadProgressTrainingInfo;

/**
 * Created by cailianjie on 2016-4-21.
 */
public class UserReadProgressTrainingInfoEx extends UserReadProgressTrainingInfo {
    Long progressId;
    String desc;
    Double finishReadRate;
    String imgUrl;
    String name;
    Long planRemainDay;
    Integer status;


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

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }
}
