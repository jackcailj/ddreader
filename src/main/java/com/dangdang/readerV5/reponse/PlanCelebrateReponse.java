package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_plan.PlanCelebrateTraining;
import com.dangdang.readerV5.read_plan.TrainingCompleteInfo;
import com.dangdang.readerV5.read_plan.TrainingRewardInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-5-4.
 */
public class PlanCelebrateReponse {
    TrainingCompleteInfo myTrainingRank;
    TrainingRewardInfo reward;
    List<TrainingCompleteInfo> trainingTops;
    List<PlanCelebrateTraining> trainings;

    Long totalFinishCount;

    public TrainingCompleteInfo getMyTrainingRank() {
        return myTrainingRank;
    }

    public void setMyTrainingRank(TrainingCompleteInfo myTrainingRank) {
        this.myTrainingRank = myTrainingRank;
    }

    public TrainingRewardInfo getReward() {
        return reward;
    }

    public void setReward(TrainingRewardInfo reward) {
        this.reward = reward;
    }

    public List<TrainingCompleteInfo> getTrainingTops() {
        return trainingTops;
    }

    public void setTrainingTops(List<TrainingCompleteInfo> trainingTops) {
        this.trainingTops = trainingTops;
    }

    public List<PlanCelebrateTraining> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<PlanCelebrateTraining> trainings) {
        this.trainings = trainings;
    }

    public Long getTotalFinishCount() {
        return totalFinishCount;
    }

    public void setTotalFinishCount(Long totalFinishCount) {
        this.totalFinishCount = totalFinishCount;
    }
}
