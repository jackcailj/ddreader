package com.dangdang.readerV5.reponse;

import com.dangdang.digital.meta.MediaTagLink;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by cailianjie on 2016-3-28.
 */
public class TagLinkInfo {
    String descs;
    Long id;
    String targetId;
    String targetName;
    Short targetType;
    String title;
    Short type;

    public TagLinkInfo(){}
    
    public TagLinkInfo(MediaTagLink link){
        this.descs = link.getDescs();
        id=link.getId();
        targetId = link.getTargetId();
        targetName=link.getTargetName();
        targetType=link.getTargetType();
        title=link.getTitle();
        type=link.getType();

    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Short getTargetType() {
        return targetType;
    }

    public void setTargetType(Short targetType) {
        this.targetType = targetType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }
}
