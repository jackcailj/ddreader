package com.dangdang.readerV5.read_plan;

/**
 * Created by cailianjie on 2016-4-29.
 */
import com.dangdang.db.digital.*;
import com.dangdang.ddframework.util.PropertyUtils;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.*;
import com.dangdang.readerV5.read_activitiy.MediaTrainingVo;
import com.ibm.icu.impl.duration.impl.Utils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanVo {

    private static final long serialVersionUID = 363675151145659337L;

    /**
     * 计划id
     */
    private Long planId;

    /**
     * 每天阅读分钟数
     */
    private Integer dayMinute;

    /**
     * 赠送积分
     */
    private Integer integral;

    /**
     * 原价（分）
     */
    private Double originPrice;

    /**
     * 计划价格（分）
     */
    private Double planPrice;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 计划描述
     */
    private String desc;

    /**
     * 封面图
     */
    private String imgUrl;

    /**
     * 计划包含的训练
     */
    private List<MediaTrainingVo> trainings;

    /**
     * 计划总的阅读完成进度
     */
    private Double finishReadRate;

    /**
     * 计划是否属于自己创建
     */
    private int isOwner;

    /**
     * 已阅读时长，单位（秒）
     */
    private Long readTime;

    /**
     * 额定总完成时长，单位（分）
     */
    private Long totalFinishTime;

    /**
     * 最大总完成时长，单位（分）
     */
    private Long maxFinishTiem;

    /**
     * 折扣系数
     */
    private Double discount;

    /**
     * 是否推荐，0 否 1 是
     */
    private Integer isRecommended;

    /**
     * 计划类型  0 收费 1 免费
     */
    private Integer isFree;

    /**
     * 是否是官方计划，0 否 1 是
     */
    private Integer isPublic;

    /**
     * 状态 0 未发布 1 已发布 2 已结束
     */
    private Integer status;

    /**
     * 用户的计划状态 1：未参与；2：已参与
     */
    private Integer userPlanStatus;

    /**
     * 创建者ID
     */
    private String creatorId;

    /**
     * 计划创建时间
     */
    private Date createTime;

    /**
     * 计划最后修改时间
     */
    private Date lastModifyTime;

    private Date expectBeginTime;//预计开始时间

    /**
     * 参与计划时间
     */
    private Date beginTime;

    /**
     * 计划真正结束时间
     */
    private Date endTime;

    /**
     * 按照目前进度，计算出的计划应该结束时间
     */
    private Date processEndTime;

    /**
     * 计划剩余天数
     */
    private Long planRemainDay;

    /**
     * 用户参与计划进程id
     */
    private Long processId;
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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getDayMinute() {
        return dayMinute;
    }

    public void setDayMinute(Integer dayMinute) {
        this.dayMinute = dayMinute;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    /**
     * 计划名称
     */
    public String getName() {
        return name;
    }

    /**
     * 计划名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 计划描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 计划描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 封面图
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 封面图
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<MediaTrainingVo> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<MediaTrainingVo> trainings) {
        this.trainings = trainings;
    }

    /**
     * 额定总完成时长，单位（分）
     */
    public Long getTotalFinishTime() {
        return totalFinishTime;
    }

    /**
     * 额定总完成时长，单位（分）
     */
    public void setTotalFinishTime(Long totalFinishTime) {
        this.totalFinishTime = totalFinishTime;
    }

    /**
     * 最大总完成时长，单位（分）
     */
    public Long getMaxFinishTiem() {
        return maxFinishTiem;
    }

    /**
     * 最大总完成时长，单位（分）
     */
    public void setMaxFinishTiem(Long maxFinishTiem) {
        this.maxFinishTiem = maxFinishTiem;
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
     * 是否推荐，0 否 1 是
     */
    public Integer getIsRecommended() {
        return isRecommended;
    }

    /**
     * 是否推荐，0 否 1 是
     */
    public void setIsRecommended(Integer isRecommended) {
        this.isRecommended = isRecommended;
    }

    /**
     * 计划类型  0 收费 1 免费
     */
    public Integer getIsFree() {
        return isFree;
    }

    /**
     * 计划类型  0 收费 1 免费
     */
    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    /**
     * 是否是官方计划，0 否 1 是
     */
    public Integer getIsPublic() {
        return isPublic;
    }

    /**
     * 是否是官方计划，0 否 1 是
     */
    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * 状态 0 未发布 1 已发布 2 已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 0 未发布 1 已发布 2 已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserPlanStatus() {
        return userPlanStatus;
    }

    public void setUserPlanStatus(Integer userPlanStatus) {
        this.userPlanStatus = userPlanStatus;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
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

    public Double getFinishReadRate() {
        return finishReadRate;
    }

    public void setFinishReadRate(Double finishReadRate) {
        this.finishReadRate = finishReadRate;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }

    public Date getExpectBeginTime() {
        return expectBeginTime;
    }

    public void setExpectBeginTime(Date expectBeginTime) {
        this.expectBeginTime = expectBeginTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getPlanRemainDay() {
        return planRemainDay;
    }

    public void setPlanRemainDay(Long planRemainDay) {
        this.planRemainDay = planRemainDay;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(Date processEndTime) {
        this.processEndTime = processEndTime;
    }

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public static PlanVo getPlanVo(String planId){
        return null;
    }


    public static List<PlanVo> getPlanVos(List<String> planIds) throws Exception {
        List<Plan> plans = PlanDb.getPlans(planIds);

        List<PlanVo> planVos = new ArrayList<>();
        for(Plan plan:plans){
            PlanVo planVo = new PlanVo();

            PropertyUtils.copyProperty(plan,planVo);

            //获取traings
            List<PlanDetailInfo> planDetails = PlanDetailDb.getPlanDetails(plan.getPlanId().toString(),false,false);
            List<String> trainingIds=Util.getFields(planDetails,"trainingId");

            planVo.setTrainings(MediaTrainingVo.getTraingVos(trainingIds));

            Double originPrice = 0d;
            Double price = 0d;
            for(MediaTrainingVo mediaTraining:planVo.getTrainings()){
                originPrice+=mediaTraining.getOriginPrice();
            }

            planVo.setOriginPrice(originPrice);

            if(planVo.getIsFree()==0){
                planVo.setPlanPrice(originPrice*planVo.getDiscount());
            }

            planVos.add(planVo);

        }

        return planVos;
    }

    public static List<PlanVo> getJoinPlanVos(List<PlanProcess> processes) throws Exception {

        //List<String> planIds = Util.getFields(processIds,"planId");
        //List<Plan> plans = PlanDb.getPlans(planIds);

        List<PlanVo> planVos = new ArrayList<>();
        for(PlanProcess planProcess:processes){

            Plan plan = PlanDb.getPlan(planProcess.getPlanId().toString());

            PlanVo planVo = new PlanVo();

            PropertyUtils.copyProperty(plan,planVo);

            //获取traings
            List<PlanProcessDetail> planProcessDetails = PlanProcessDetailDb.getPlanProcessByProcessId(planProcess.getPpId().toString());
            List<String> trainingIds=Util.getFields(planProcessDetails,"trainingId");

            planVo.setTrainings(MediaTrainingVo.getTraingVos(trainingIds));

            Double originPrice = 0d;
            Double price = 0d;
            for(MediaTrainingVo mediaTraining:planVo.getTrainings()){
                originPrice+=mediaTraining.getOriginPrice();
            }

            planVo.setOriginPrice(originPrice);

            if(planVo.getIsFree()==null || planVo.getIsFree()==0){
                planVo.setPlanPrice(originPrice*planVo.getDiscount());
            }


            planVos.add(planVo);

        }

        return planVos;
    }


}