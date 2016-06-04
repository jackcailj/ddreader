package com.dangdang.readerV5.read_plan;

/**
 * Created by cailianjie on 2016-5-4.
 */
public class PlanCelebrateTraining {
    String coverPic;
    String title;
    Double totalFinishRate;
    Integer trainingStatus;
    Integer useDays;

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTotalFinishRate() {
        return totalFinishRate;
    }

    public void setTotalFinishRate(Double totalFinishRate) {
        this.totalFinishRate = totalFinishRate;
    }

    public Integer getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Integer trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }
}
