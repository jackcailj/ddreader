package com.dangdang.readerV5.reponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cailianjie on 2016-1-19.
 */
public class ExperienceInfoData {
    String afterRanking;
    String beforeRanking;

    CatetoryData catetoryData;

    Integer finishReadCount;
    Integer readingDays;
    Long readingTime;
    Integer releaseArticleCount;
    Integer releaseEssayCount;
    Integer releaseStraegyCount;
    Integer shareCount;
    Long totalWords;

    Map<String,Integer> habit;

    FirstBook firstBook;

    public ExperienceInfoData(){
        habit =new HashMap<String,Integer>();
        for(Integer i=0;i<24;i++){
            habit.put(i.toString(),0);
        }
    }


    public String getAfterRanking() {
        return afterRanking;
    }

    public void setAfterRanking(String afterRanking) {
        this.afterRanking = afterRanking;
    }

    public String getBeforeRanking() {
        return beforeRanking;
    }

    public void setBeforeRanking(String beforeRanking) {
        this.beforeRanking = beforeRanking;
    }

    public CatetoryData getCatetoryData() {
        return catetoryData;
    }

    public void setCatetoryData(CatetoryData catetoryData) {
        this.catetoryData = catetoryData;
    }

    public Integer getFinishReadCount() {
        return finishReadCount;
    }

    public void setFinishReadCount(Integer finishReadCount) {
        this.finishReadCount = finishReadCount;
    }

    public Integer getReadingDays() {
        return readingDays;
    }

    public void setReadingDays(Integer readingDays) {
        this.readingDays = readingDays;
    }

    public Long getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Long readingTime) {
        this.readingTime = readingTime;
    }

    public Integer getReleaseArticleCount() {
        return releaseArticleCount;
    }

    public void setReleaseArticleCount(Integer releaseArticleCount) {
        this.releaseArticleCount = releaseArticleCount;
    }

    public Integer getReleaseEssayCount() {
        return releaseEssayCount;
    }

    public void setReleaseEssayCount(Integer releaseEssayCount) {
        this.releaseEssayCount = releaseEssayCount;
    }

    public Integer getReleaseStraegyCount() {
        return releaseStraegyCount;
    }

    public void setReleaseStraegyCount(Integer releaseStraegyCount) {
        this.releaseStraegyCount = releaseStraegyCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Long getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Long totalWords) {
        this.totalWords = totalWords;
    }

    public Map<String, Integer> getHabit() {
        return habit;
    }

    public void setHabit(Map<String, Integer> habit) {
        this.habit = habit;
    }

    public FirstBook getFirstBook() {
        return firstBook;
    }

    public void setFirstBook(FirstBook firstBook) {
        this.firstBook = firstBook;
    }
}
