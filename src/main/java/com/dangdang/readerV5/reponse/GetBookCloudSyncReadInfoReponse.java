package com.dangdang.readerV5.reponse;

import com.dangdang.BaseComment.meta.CloudBookMark;
import com.dangdang.BaseComment.meta.CloudBookNote;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-23.
 */
public class GetBookCloudSyncReadInfoReponse {
    List<CloudBookMark> markInfo;
    List<CloudBookNote> noteInfo;
    Long productId;

    public List<CloudBookMark> getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(List<CloudBookMark> markInfo) {
        this.markInfo = markInfo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<CloudBookNote> getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(List<CloudBookNote> noteInfo) {
        this.noteInfo = noteInfo;
    }
}
