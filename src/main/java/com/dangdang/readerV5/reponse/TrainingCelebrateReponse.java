package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_plan.MediaBaseInfo;
import com.dangdang.readerV5.read_plan.TrainingCompleteInfo;
import com.dangdang.readerV5.read_plan.TrainingRewardInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-5-3.
 */
public class TrainingCelebrateReponse {
    MediaBaseInfo media;
    TrainingCompleteInfo myTrainingRank;
    TrainingRewardInfo reward;
    List<TrainingCompleteInfo> trainingTops;

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


    public MediaBaseInfo getMedia() {
        return media;
    }

    public void setMedia(MediaBaseInfo media) {
        this.media = media;
    }
}
