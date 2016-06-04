package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_activitiy.MediaTrainingVo;

import java.util.List;

/**
 * Created by cailianjie on 2016-5-18.
 */
public class RecommendTrainingReponse {
    List<MediaTrainingVo> trainings;

    public List<MediaTrainingVo> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<MediaTrainingVo> trainings) {
        this.trainings = trainings;
    }
}
