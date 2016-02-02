package com.dangdang.readerV5.personal_center.cloud_sync_read;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by cailianjie on 2015-9-24.
 */
public class CloudExperienceInfoEx {
    private Long id;
    private long custId;
    private long productId;
    private long recordTime;
    private short type;
    private Remarks remarks;
    private String deviceType;
    String lineationContent;
    String noteContent;
    String status;


    public CloudExperienceInfoEx() {
    }

    public CloudExperienceInfoEx(long custId, long productId, long recordTime,
                               short type, Remarks remarks) {
        this.custId = custId;
        this.productId = productId;
        this.recordTime = recordTime;
        this.type = type;
        this.remarks = remarks;
    }

    public CloudExperienceInfoEx(long custId, long productId, long recordTime,
                               short type, Remarks remarks, String deviceType) {
        this.custId = custId;
        this.productId = productId;
        this.recordTime = recordTime;
        this.type = type;
        this.remarks = remarks;
        this.deviceType = deviceType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCustId() {
        return this.custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public short getType() {
        return this.type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Remarks getRemarks() {
        return this.remarks;
    }

    public void setRemarks(Remarks remarks) {
        this.remarks = remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = JSONObject.parseObject(remarks,Remarks.class);
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLineationContent() {
        return lineationContent;
    }

    public void setLineationContent(String lineationContent) {
        this.lineationContent = lineationContent;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
