package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Plan;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-18.
 */
public class PlanInfoEx extends Plan{
    Long originPrice;
    Long planPrice;
    Long planRemainDay;
    Long processEndTime;
    Long processId;
    Long readTime;
    Long totalFinishTime;
    Integer userPlanStatus;
    List<TrainingInfoEx> trainings;
    Integer isOwner;
    String creatorId;

    public Long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Long originPrice) {
        this.originPrice = originPrice;
    }

    public Long getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Long planPrice) {
        this.planPrice = planPrice;
    }

    public Long getPlanRemainDay() {
        return planRemainDay;
    }

    public void setPlanRemainDay(Long planRemainDay) {
        this.planRemainDay = planRemainDay;
    }

    public Long getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(Long processEndTime) {
        this.processEndTime = processEndTime;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }

    @Override
    public Long getTotalFinishTime() {
        return totalFinishTime;
    }

    @Override
    public void setTotalFinishTime(Long totalFinishTime) {
        this.totalFinishTime = totalFinishTime;
    }

    public Integer getUserPlanStatus() {
        return userPlanStatus;
    }

    public void setUserPlanStatus(Integer userPlanStatus) {
        this.userPlanStatus = userPlanStatus;
    }

    public List<TrainingInfoEx> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingInfoEx> trainings) {
        this.trainings = trainings;
    }

    public Integer getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Integer isOwner) {
        this.isOwner = isOwner;
    }


    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
