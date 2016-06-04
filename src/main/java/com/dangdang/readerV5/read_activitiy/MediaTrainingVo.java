package com.dangdang.readerV5.read_activitiy;

/**
 * Created by cailianjie on 2016-4-29.
 */
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.MediaTrainingDb;
import com.dangdang.ddframework.util.PropertyUtils;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.readerV5.reponse.UserBaseInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:训练vo
 * All Rights Reserved.
 * @version 1.0  2016-3-15 上午11:08:49  by 傅作魁（fuzuokui@dangdang.com）创建
 */
public class MediaTrainingVo  {

    private static final long serialVersionUID = 7656416874328470589L;

    /**
     * 训练主键id
     */
    private Long mtId;

    /**
     * 单品id
     */
    private Long mediaId;

    /**
     * 额定阅读完成时间，单位（分）
     */
    private Long totalFinishTime;

    /**
     * 最大阅读完成时间，单位（分）
     */
    private Long maxFinishTime;

    /**
     * 折扣系数
     */
    private Double discount;

    /**
     * 总阅读量(对应总页数)
     */
    private Long wordcnt;

    /**
     * 日需完成阅读量
     */
    private Long wordcntAvg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 是否拥有全本权限：1、是；0、否
     */
    private Integer isWholeAuthority;

    /**
     * 训练状态： 0 未开始 1 进行中 2 已结束
     */
    private int trainingStatus;

    private String title;//书名

    private String authorName;//作者名

    private String descs;//书简介

    private String coverPic;//书籍封面

    private Integer shelfStatus;//上下架状态

    private Integer originPrice;//书籍价格

    private Integer trainingPrice;//训练价格

    private Double todayFinishRate;//今日训练完成百分比

    private Double totalFinishRate;//训练总完成百分比

    private Double dailyTargetFinishRate;//每天目标完成百分比

    private Double nextFinishRate;//翻阅日历追加百分比=训练的每日阅读量 * 天数 / 这个计划的训练总数

    private Long wordcntRead;//已完成阅读量

    private Integer joinUserCount;//训练参与人数

    private Integer dayMinute;//训练的每日阅读时长

    private List<UserBaseInfo> joinUsers;//训练参与人信息

    private Integer baseIntegral;
    private Integer baseExperience;
    private Integer baseMoney;

    private Integer useDays;//完成训练，用的天数

    public Long getMtId() {
        return mtId;
    }

    public void setMtId(Long mtId) {
        this.mtId = mtId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * 额定阅读完成时间，单位（分）
     */
    public Long getTotalFinishTime() {
        return totalFinishTime;
    }

    /**
     * 额定阅读完成时间，单位（分）
     */
    public void setTotalFinishTime(Long totalFinishTime) {
        this.totalFinishTime = totalFinishTime;
    }

    /**
     * 最大阅读完成时间，单位（分）
     */
    public Long getMaxFinishTime() {
        return maxFinishTime;
    }

    /**
     * 最大阅读完成时间，单位（分）
     */
    public void setMaxFinishTime(Long maxFinishTime) {
        this.maxFinishTime = maxFinishTime;
    }

    /**
     * 折扣系数
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * 折扣系数
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    /**
     * 总阅读量
     */
    public Long getWordcnt() {
        return wordcnt;
    }

    /**
     * 总阅读量
     */
    public void setWordcnt(Long wordcnt) {
        this.wordcnt = wordcnt;
    }

    /**
     * 每日阅读量
     */
    public Long getWordcntAvg() {
        return wordcntAvg;
    }

    /**
     * 每日阅读量
     */
    public void setWordcntAvg(Long wordcntAvg) {
        this.wordcntAvg = wordcntAvg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getIsWholeAuthority() {
        return isWholeAuthority;
    }

    public void setIsWholeAuthority(Integer isWholeAuthority) {
        this.isWholeAuthority = isWholeAuthority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public Integer getTrainingPrice() {
        return trainingPrice;
    }

    public void setTrainingPrice(Integer trainingPrice) {
        this.trainingPrice = trainingPrice;
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

    public Long getWordcntRead() {
        return wordcntRead;
    }

    public void setWordcntRead(Long wordcntRead) {
        this.wordcntRead = wordcntRead;
    }

    public Integer getJoinUserCount() {
        return joinUserCount;
    }

    public void setJoinUserCount(Integer joinUserCount) {
        this.joinUserCount = joinUserCount;
    }

    public List<UserBaseInfo> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<UserBaseInfo> joinUsers) {
        this.joinUsers = joinUsers;
    }

    public Double getNextFinishRate() {
        return nextFinishRate;
    }

    public void setNextFinishRate(Double nextFinishRate) {
        this.nextFinishRate = nextFinishRate;
    }

    public Integer getDayMinute() {
        return dayMinute;
    }

    public void setDayMinute(Integer dayMinute) {
        this.dayMinute = dayMinute;
    }

    public Double getDailyTargetFinishRate() {
        return dailyTargetFinishRate;
    }

    public void setDailyTargetFinishRate(Double dailyTargetFinishRate) {
        this.dailyTargetFinishRate = dailyTargetFinishRate;
    }

    public int getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(int trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public Integer getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(Integer shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getBaseIntegral() {
        return baseIntegral;
    }

    public void setBaseIntegral(Integer baseIntegral) {
        this.baseIntegral = baseIntegral;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Integer getBaseMoney() {
        return baseMoney;
    }

    public void setBaseMoney(Integer baseMoney) {
        this.baseMoney = baseMoney;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }


    public static List<MediaTrainingVo> getTraingVos(List<String> traingIds) throws Exception {

        List<MediaTraining> trainings = MediaTrainingDb.getTrainings(traingIds,true,-1);

        List<MediaTrainingVo> mediaTrainingVos = new ArrayList<>();
        for(MediaTraining training:trainings){
            MediaTrainingVo mediaTrainingVo = new MediaTrainingVo();

            PropertyUtils.copyProperty(training,mediaTrainingVo);

            Media media = MediaDb.getMedia(training.getMediaId().toString());
            mediaTrainingVo.setOriginPrice(media.getPrice());
            mediaTrainingVo.setShelfStatus(media.getShelfStatus().intValue());
            mediaTrainingVo.setWordcnt(media.getWordCnt());

            double traingPrice=media.getPrice()*training.getDiscount();
            mediaTrainingVo.setTrainingPrice((int) traingPrice);


            mediaTrainingVos.add(mediaTrainingVo);
        }

        return mediaTrainingVos;
    }

}