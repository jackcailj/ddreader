package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.Plan;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-19.
 */
public class MyPlanListInfo extends Plan{
    String showPrice;
    Long planPrice;
    Long planReadTime;
    Long planRemainDay;
    List<String> bookNameList;
    Long processId;
    Long planFinishTime;
    Long joinPlanDate;
    Double finishReadRate;
    Long expectFinishDays;

    public String getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(String showPrice) {
        this.showPrice = showPrice;
    }

    public Long getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Long planPrice) {
        this.planPrice = planPrice;
    }

    public Long getPlanReadTime() {
        return planReadTime;
    }

    public void setPlanReadTime(Long planReadTime) {
        this.planReadTime = planReadTime;
    }

    public Long getPlanRemainDay() {
        return planRemainDay;
    }

    public void setPlanRemainDay(Long planRemainDay) {
        this.planRemainDay = planRemainDay;
    }

    public List<String> getBookNameList() {
        return bookNameList;
    }

    public void setBookNameList(List<String> bookNameList) {
        this.bookNameList = bookNameList;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getPlanFinishTime() {
        return planFinishTime;
    }

    public void setPlanFinishTime(Long planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    public Long getJoinPlanDate() {
        return joinPlanDate;
    }

    public void setJoinPlanDate(Long joinPlanDate) {
        this.joinPlanDate = joinPlanDate;
    }

    public Double getFinishReadRate() {
        return finishReadRate;
    }

    public void setFinishReadRate(Double finishReadRate) {
        this.finishReadRate = finishReadRate;
    }

    public Long getExpectFinishDays() {
        return expectFinishDays;
    }

    public void setExpectFinishDays(Long expectFinishDays) {
        this.expectFinishDays = expectFinishDays;
    }
}
