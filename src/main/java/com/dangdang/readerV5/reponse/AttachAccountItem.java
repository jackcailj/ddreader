package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-8-13.
 */
public class AttachAccountItem {
    private Long attachAccountItemsId;
    private Long custId;
    private Integer faceValue;
    private Integer surplusMoney;
    private String consumeSource;
    private Integer modifiedVersion;
    private Date effectiveDate;
    private String platformNo;
    private Integer status;
    private String activityCode;
    private Date creationDateString;
    private Date lastModifiedDate;
    private String deviceType;
    private String activityName;


    public Long getAttachAccountItemsId() {
        return attachAccountItemsId;
    }

    public void setAttachAccountItemsId(Long attachAccountItemsId) {
        this.attachAccountItemsId = attachAccountItemsId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(Integer surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public String getConsumeSource() {
        return consumeSource;
    }

    public void setConsumeSource(String consumeSource) {
        this.consumeSource = consumeSource;
    }

    public Integer getModifiedVersion() {
        return modifiedVersion;
    }

    public void setModifiedVersion(Integer modifiedVersion) {
        this.modifiedVersion = modifiedVersion;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Date getCreationDateString() {
        return creationDateString;
    }

    public void setCreationDateString(Date creationDateString) {
        this.creationDateString = creationDateString;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
