package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2015-9-9.
 */
public class GetMyGiveListData {
    String authorName;
    String coverPic;
    String custImg;
    String desc;
    Long getDate;
    Long mediaId;
    Integer mediaType;
    String nickName;
    Long saleId;
    Integer status;
    String title;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getCustImg() {
        return custImg;
    }

    public void setCustImg(String custImg) {
        this.custImg = custImg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getGetDate() {
        return getDate;
    }

    public void setGetDate(Long getDate) {
        this.getDate = getDate;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
