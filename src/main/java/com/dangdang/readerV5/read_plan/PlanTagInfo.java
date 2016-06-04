package com.dangdang.readerV5.read_plan;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class PlanTagInfo {
    Long createTime;
    Integer isPlanTag;
    Integer isRecommend;
    Long lastModifiedTime;
    Long tagId;
    String tagName;
    Integer tagType;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getIsPlanTag() {
        return isPlanTag;
    }

    public void setIsPlanTag(Integer isPlanTag) {
        this.isPlanTag = isPlanTag;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }
}
