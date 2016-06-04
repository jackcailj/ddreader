package com.dangdang.readerV5.reponse;

import com.dangdang.db.digital.UserTrainingReadProgress;

/**
 * Created by cailianjie on 2016-4-22.
 */
public class GetUserReadProgressReponse {
    UserTrainingReadProgress readProgressInfo;

    public UserTrainingReadProgress getReadProgressInfo() {
        return readProgressInfo;
    }

    public void setReadProgressInfo(UserTrainingReadProgress readProgressInfo) {
        this.readProgressInfo = readProgressInfo;
    }
}
