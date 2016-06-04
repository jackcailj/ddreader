package com.dangdang.readerV5.read_activitiy;

/**
 * Created by cailianjie on 2016-4-29.
 */
import com.dangdang.readerV5.reponse.UserBaseInfo;

import java.util.Date;
import java.util.List;

/**
 * PlanActivitiy Entity.
 */
public class PlanActivityVo{

    /**
     *
     */
    private static final long serialVersionUID = -6932488485246978977L;
    /**
     * 活动id
     */
    private Long paId;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 吧id
     */
    private Long barId;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * digestID
     */
    private Long digestId;
    /**
     * 帖子id
     */
    private Long articleId;
    /**
     * 最小参与人数
     */
    private Long minFinishPeople;
    /**
     * 状态(-1:未开始,0:未结束,1:结束)
     */
    private Integer status;
    /**
     * 活动内容
     */
    private String descs;
    /**
     * 图片
     */
    private String imgUrl;
    /**
     * 实际加入人数
     */
    private Long joinPeople;
    /**
     * 完成人数
     */
    private Long finishPeople;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 创建者custID
     */
    private Long creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 计划包含的训练
     */
    private List<MediaTrainingVo> trainings;
    /**
     * 是否加入活动(0:未加入  1:加入)
     */
    private Integer isJoin;
    /**
     * 是否加入计划(0:未加入  1:加入)
     */
    private Integer isJoinPlan;
    /**
     * 剩余天数
     */
    private Integer remainDays;
    /**
     * 完成度
     */
    private int completion;
    /**
     * 额定总完成时长，单位（分）
     */
    private Long totalFinishTime;
    /**
     * 计划价格
     */
    private Double planPrice;
    /**
     * 参与人列表
     */
    private List<UserBaseInfo> userList;
    /**
     * 活动奖励
     */
    private String rewardsMsg;
    /**
     * 是否是活动创建者
     */
    private int isCreator;
    /**
     * 基础积分
     */
    private Integer baseIntegral;
    /**
     * 基础经验
     */
    private Integer baseExperience;
    /**
     * 基础银铃铛
     */
    private Integer baseMoney;
    /**
     * 开始时间剩余天数
     */
    private Integer remainStartDays;
    /**
     * 是否免费
     */
    private Integer isFree;


    public Integer getIsFree() {
        return isFree;
    }
    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }
    public Integer getRemainStartDays() {
        return remainStartDays;
    }
    public void setRemainStartDays(Integer remainStartDays) {
        this.remainStartDays = remainStartDays;
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
    public int getIsCreator() {
        return isCreator;
    }
    public void setIsCreator(int isCreator) {
        this.isCreator = isCreator;
    }
    public String getRewardsMsg() {
        return rewardsMsg;
    }
    public void setRewardsMsg(String rewardsMsg) {
        this.rewardsMsg = rewardsMsg;
    }
    public List<UserBaseInfo> getUserList() {
        return userList;
    }
    public void setUserList(List<UserBaseInfo> userList) {
        this.userList = userList;
    }
    public Long getTotalFinishTime() {
        return totalFinishTime;
    }
    public void setTotalFinishTime(Long totalFinishTime) {
        this.totalFinishTime = totalFinishTime;
    }
    public Double getPlanPrice() {
        return planPrice;
    }
    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }
    public Integer getRemainDays() {
        return remainDays;
    }
    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }

    public int getCompletion() {
        return completion;
    }
    public void setCompletion(int completion) {
        this.completion = completion;
    }
    public Integer getIsJoin() {
        return isJoin;
    }
    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public List<MediaTrainingVo> getTrainings() {
        return trainings;
    }
    public void setTrainings(List<MediaTrainingVo> trainings) {
        this.trainings = trainings;
    }
    public Long getPaId() {
        return paId;
    }
    public void setPaId(Long paId) {
        this.paId = paId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getBarId() {
        return barId;
    }
    public void setBarId(Long barId) {
        this.barId = barId;
    }
    public Long getPlanId() {
        return planId;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    public Long getDigestId() {
        return digestId;
    }
    public void setDigestId(Long digestId) {
        this.digestId = digestId;
    }
    public Long getArticleId() {
        return articleId;
    }
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Long getMinFinishPeople() {
        return minFinishPeople;
    }
    public void setMinFinishPeople(Long minFinishPeople) {
        this.minFinishPeople = minFinishPeople;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescs() {
        return descs;
    }
    public void setDescs(String descs) {
        this.descs = descs;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Long getJoinPeople() {
        return joinPeople;
    }
    public void setJoinPeople(Long joinPeople) {
        this.joinPeople = joinPeople;
    }
    public Long getFinishPeople() {
        return finishPeople;
    }
    public void setFinishPeople(Long finishPeople) {
        this.finishPeople = finishPeople;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Long getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
    public Integer getIsJoinPlan() {
        return isJoinPlan;
    }
    public void setIsJoinPlan(Integer isJoinPlan) {
        this.isJoinPlan = isJoinPlan;
    }


}
