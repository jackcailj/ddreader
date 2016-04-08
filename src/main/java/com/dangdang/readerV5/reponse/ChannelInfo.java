package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2016-3-24.
 */
public class ChannelInfo {
    Long id;
    String title;
    String icon;
    Integer subNumber;
    String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(Integer subNumber) {
        this.subNumber = subNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
