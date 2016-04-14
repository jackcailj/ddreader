package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class PageLayoutInfo {
    String actionName;
    String entityCode;
    String entityCodeParamName;
    Integer isReturnData;
    String type;

    PageLayoutInfoData data;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCodeParamName() {
        return entityCodeParamName;
    }

    public void setEntityCodeParamName(String entityCodeParamName) {
        this.entityCodeParamName = entityCodeParamName;
    }

    public Integer getIsReturnData() {
        return isReturnData;
    }

    public void setIsReturnData(Integer isReturnData) {
        this.isReturnData = isReturnData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PageLayoutInfoData getData() {
        return data;
    }

    public void setData(PageLayoutInfoData data) {
        this.data = data;
    }
}
