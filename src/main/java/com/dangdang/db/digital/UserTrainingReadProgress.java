package com.dangdang.db.digital;

/**
 * Created by cailianjie on 2016-4-22.
 */
public class UserTrainingReadProgress {
    String custId;
    Double dailyTargetFinishRate;
    Integer hasFullAuthority;
    Long mediaId;
    Double todayFinishRate;
    Double totalFinishRate;
    Long trainingId;
    Long wordCntAvg;
    Long wordcnt;
    Long chapter;
    Long chapterOffset;
    Long lastSynTime;
    byte[] readCoverage;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Double getDailyTargetFinishRate() {
        return dailyTargetFinishRate;
    }

    public void setDailyTargetFinishRate(Double dailyTargetFinishRate) {
        this.dailyTargetFinishRate = dailyTargetFinishRate;
    }

    public Integer getHasFullAuthority() {
        return hasFullAuthority;
    }

    public void setHasFullAuthority(Integer hasFullAuthority) {
        this.hasFullAuthority = hasFullAuthority;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Double getTodayFinishRate() {
        return todayFinishRate;
    }

    public void setTodayFinishRate(Double todayFinishRate) {
        this.todayFinishRate = todayFinishRate;
    }

    public Double getTotalFinishRate() {
        return totalFinishRate;
    }

    public void setTotalFinishRate(Double totalFinishRate) {
        this.totalFinishRate = totalFinishRate;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Long getWordCntAvg() {
        return wordCntAvg;
    }

    public void setWordCntAvg(Long wordCntAvg) {
        this.wordCntAvg = wordCntAvg;
    }

    public Long getWordcnt() {
        return wordcnt;
    }

    public void setWordcnt(Long wordcnt) {
        this.wordcnt = wordcnt;
    }

    public Long getChapter() {
        return chapter;
    }

    public void setChapter(Long chapter) {
        this.chapter = chapter;
    }

    public Long getChapterOffset() {
        return chapterOffset;
    }

    public void setChapterOffset(Long chapterOffset) {
        this.chapterOffset = chapterOffset;
    }

    public Long getLastSynTime() {
        return lastSynTime;
    }

    public void setLastSynTime(Long lastSynTime) {
        this.lastSynTime = lastSynTime;
    }

    public byte[] getReadCoverage() {
        return readCoverage;
    }

    public void setReadCoverage(byte[] readCoverage) {
        this.readCoverage = readCoverage;
    }
}
