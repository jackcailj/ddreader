package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class PayingProduct {
    Integer depositGiftReadPrice;
    Integer depositMoney;
    Integer depositReadPrice;
    Integer effectiveDay;
    Date endTime;
    String relationProductId;
    Date startTime;

    public Integer getDepositGiftReadPrice() {
        return depositGiftReadPrice;
    }

    public void setDepositGiftReadPrice(Integer depositGiftReadPrice) {
        this.depositGiftReadPrice = depositGiftReadPrice;
    }

    public Integer getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(Integer depositMoney) {
        this.depositMoney = depositMoney;
    }

    public Integer getDepositReadPrice() {
        return depositReadPrice;
    }

    public void setDepositReadPrice(Integer depositReadPrice) {
        this.depositReadPrice = depositReadPrice;
    }

    public Integer getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(Integer effectiveDay) {
        this.effectiveDay = effectiveDay;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRelationProductId() {
        return relationProductId;
    }

    public void setRelationProductId(String relationProductId) {
        this.relationProductId = relationProductId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
