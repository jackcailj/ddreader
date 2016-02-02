package com.dangdang.readerV5.personal_center.cloud_sync_read;

/**
 * Created by cailianjie on 2015-9-21.
 */
public class MarkInfo {
    Integer characterIndex;
    String custId;
    String status;
    Integer chaptersIndex;
    String markInfo;
    Long modifyTime;
    Long clientOperateTime;
    Long productId;
    String bookmodversion;


    public Integer getCharacterIndex() {
        return characterIndex;
    }

    public void setCharacterIndex(Integer characterIndex) {
        this.characterIndex = characterIndex;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getChaptersIndex() {
        return chaptersIndex;
    }

    public void setChaptersIndex(Integer chaptersIndex) {
        this.chaptersIndex = chaptersIndex;
    }

    public String getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(String markInfo) {
        this.markInfo = markInfo;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getClientOperateTime() {
        return clientOperateTime;
    }

    public void setClientOperateTime(Long clientOperateTime) {
        this.clientOperateTime = clientOperateTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBookmodversion() {
        return bookmodversion;
    }

    public void setBookmodversion(String bookmodversion) {
        this.bookmodversion = bookmodversion;
    }
}
