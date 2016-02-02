package com.dangdang.readerV5.reponse;

import com.dangdang.BaseComment.meta.CloudReadingProgress;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-23.
 */
public class GetBookCloudSyncReadProgressInfoReponse {
    CloudReadingProgress bookReadingProgress;
    Long totalReadingTime;

    public CloudReadingProgress getBookReadingProgress() {
        return bookReadingProgress;
    }

    public void setBookReadingProgress(CloudReadingProgress bookReadingProgress) {
        this.bookReadingProgress = bookReadingProgress;
    }

    public Long getTotalReadingTime() {
        return totalReadingTime;
    }

    public void setTotalReadingTime(Long totalReadingTime) {
        this.totalReadingTime = totalReadingTime;
    }
}
