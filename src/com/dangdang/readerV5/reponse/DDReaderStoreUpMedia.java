package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-7-31.
 */
public class DDReaderStoreUpMedia {

    String authorName;
    String bookName;
    String coverPic;
    String ebook;
    String editorRecommend;
    Long productId;
    Date storeDateLong;


    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getEbook() {
        return ebook;
    }

    public void setEbook(String ebook) {
        this.ebook = ebook;
    }

    public String getEditorRecommend() {
        return editorRecommend;
    }

    public void setEditorRecommend(String editorRecommend) {
        this.editorRecommend = editorRecommend;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getStoreDateLong() {
        return storeDateLong;
    }

    public void setStoreDateLong(Date storeDateLong) {
        this.storeDateLong = storeDateLong;
    }
}
