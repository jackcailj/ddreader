package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class MediaPacketInfo {
    String advice;
    Date creationDate;
    String nickName;
    Integer puts;
    Integer status;
    Integer total;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPuts() {
        return puts;
    }

    public void setPuts(Integer puts) {
        this.puts = puts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
