package com.dangdang.readerV5.personal_center.cloud_sync_read;

/**
 * Created by cailianjie on 2015-9-21.
 */
public class NoteInfo {
    Integer  characterEndIndex;
    String custId;
    String status;
    Integer chaptersIndex;
    String noteInfo;
    Integer characterStartIndex;
    Long modifyTime;
    Long clientOperateTime;
    String callOutInfo;
    Long productId;
    String bookmodversion;

    public Integer getCharacterEndIndex() {
        return characterEndIndex;
    }

    public void setCharacterEndIndex(Integer characterEndIndex) {
        this.characterEndIndex = characterEndIndex;
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

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    public Integer getCharacterStartIndex() {
        return characterStartIndex;
    }

    public void setCharacterStartIndex(Integer characterStartIndex) {
        this.characterStartIndex = characterStartIndex;
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

    public String getCallOutInfo() {
        return callOutInfo;
    }

    public void setCallOutInfo(String callOutInfo) {
        this.callOutInfo = callOutInfo;
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
