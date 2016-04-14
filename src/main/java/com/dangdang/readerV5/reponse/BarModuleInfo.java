package com.dangdang.readerV5.reponse;

/**
 * Created by cailianjie on 2016-3-29.
 */
public class BarModuleInfo {
    Long barModuleId;
    String moduleName;
    Byte showNum;
    Byte type;

    public Long getBarModuleId() {
        return barModuleId;
    }

    public void setBarModuleId(Long barModuleId) {
        this.barModuleId = barModuleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Byte getShowNum() {
        return showNum;
    }

    public void setShowNum(Byte showNum) {
        this.showNum = showNum;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
