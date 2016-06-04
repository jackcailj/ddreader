package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.digital.meta.Plan;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class ReadPlanInfo extends Plan{
    List<MediaTraining> trainings;

    Long planPrice;


    public List<MediaTraining> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<MediaTraining> trainings) {
        this.trainings = trainings;
    }

    public Long getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Long planPrice) {
        this.planPrice = planPrice;
    }
}
