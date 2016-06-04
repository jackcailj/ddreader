package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_plan.ReadTrainingTopInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class ReadTrainingTopReponse {

    //TrainingRank trainingTops;
    List<ReadTrainingTopInfo> trainingTops;

    public List<ReadTrainingTopInfo> getTrainingTops() {
        return trainingTops;
    }

    public void setTrainingTops(List<ReadTrainingTopInfo> trainingTops) {
        this.trainingTops = trainingTops;
    }
}
