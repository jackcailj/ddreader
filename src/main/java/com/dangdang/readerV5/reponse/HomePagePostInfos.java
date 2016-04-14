package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-29.
 */
public class HomePagePostInfos {
    List<HomePagePostInfo> articleContent;

    BarModuleInfo module;

    public List<HomePagePostInfo> getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(List<HomePagePostInfo> articleContent) {
        this.articleContent = articleContent;
    }

    public BarModuleInfo getModule() {
        return module;
    }

    public void setModule(BarModuleInfo module) {
        this.module = module;
    }
}
