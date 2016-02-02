package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class MediaInfoPacket {
    String authorPenname;
    String descs;
    Integer isStore;
    Long mediaId;
    Integer mediaType;
    Long saleId;
    String title;

    public String getAuthorPenname() {
        return authorPenname;
    }

    public void setAuthorPenname(String authorPenname) {
        this.authorPenname = authorPenname;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Integer getIsStore() {
        return isStore;
    }

    public void setIsStore(Integer isStore) {
        this.isStore = isStore;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
