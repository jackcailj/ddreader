package com.dangdang.readerV5.reponse;

import java.util.Date;

/**
 * Created by cailianjie on 2015-7-31.
 */
public class DDReaderStoreUpArticle {
    String articleId;
    String cardType;
    String from;
    String remark;
    String smallPic1Path;
    String smallPic2Path;
    String smallPic3Path;
    Date storeDateLong;
    String title;
    Integer type;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
