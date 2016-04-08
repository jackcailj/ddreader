package com.dangdang.readerV5.homepage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.db.bookbar.BarModuleContentDb;
import com.dangdang.db.bookbar.BarModuleDb;
import com.dangdang.db.comment.PraiseInfoDb;
import com.dangdang.db.digital.*;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.*;
import com.dangdang.digital.meta.Channel;
import com.dangdang.readerV5.reponse.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class PageLayoutDetail extends FixtureBase{

    ReponseV2<PageLayoutDetailReponse> reponseResult;

    public PageLayoutDetail(){}



    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<PageLayoutDetailReponse>>(){});
        String block=reponseResult.getData().getList().get(0).getData().getData().getBlock();
        HomePageBannerList homePageBannerList = JSONObject.parseObject(block,new TypeReference<HomePageBannerList>(){});
        reponseResult.getData().getList().get(0).getData().getData().setBlockObject(homePageBannerList);
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            //获取页面布局
            List<MediaLayoutDetail> layoutDetails = MediaLayoutDetailDb.getLayoutDetail("HMPG");

            PageLayoutDetailReponse expectPageLayoutDetailReponse = new PageLayoutDetailReponse();
            List<PageLayoutInfo> pageLayoutInfos = new ArrayList<PageLayoutInfo>();
            expectPageLayoutDetailReponse.setList(pageLayoutInfos);
            //获取页面布局下的数据
            for(MediaLayoutDetail detail:layoutDetails){
                //设置布局信息
                PageLayoutInfo pageLayoutInfo = new PageLayoutInfo();
                pageLayoutInfo.setActionName(detail.getActionName());
                pageLayoutInfo.setEntityCode(detail.getEntityCode());
                pageLayoutInfo.setEntityCodeParamName(detail.getEntityCodeParamName());
                pageLayoutInfo.setType(detail.getType());
                pageLayoutInfos.add(pageLayoutInfo);

                PageLayoutInfoData pageLayoutInfoData = new PageLayoutInfoData();
                pageLayoutInfo.setData(pageLayoutInfoData);

                PageLayoutInfoDataDetail pageLayoutInfoDataDetail = new PageLayoutInfoDataDetail();
                pageLayoutInfoData.setData(pageLayoutInfoDataDetail);

                switch (detail.getType()){
                    case "barsquare": {
                        //通过书吧模块获取书吧信息
                        /*PageLayoutInfoData pageLayoutInfoData = new PageLayoutInfoData();
                        PageLayoutInfoDataDetail pageLayoutInfoDataDetail = new PageLayoutInfoDataDetail();*/

                        //pageLayoutInfo.setData(pageLayoutInfoData);

                       // pageLayoutInfoData.setData(pageLayoutInfoDataDetail);
                        List<BarContent> barModuleContents = BarModuleContentDb.getBarModule(detail.getEntityCode());
                        List<SquareInfo> squareInfo =new ArrayList<SquareInfo>();
                        pageLayoutInfoDataDetail.setSquareInfo(squareInfo);

                        SquareInfo info = new SquareInfo();
                        info.setBarContent(barModuleContents);
                        squareInfo.add(info);
                    }
                        break;
                    case "block":{
                        MediaBlock banner = MediaBlockDb.getBlock(detail.getEntityCode());
                        HomePageBannerList homePageBannerList = JSONObject.parseObject(banner.getContent(),new TypeReference<HomePageBannerList>(){});
                        pageLayoutInfoDataDetail.setBlockObject(homePageBannerList);

                    }
                        break;
                    case "column": {

                        ColumnParam columnParam= JSONObject.parseObject(detail.getCustomContent(),new TypeReference<ColumnParam>(){});
                        Integer dataNum =columnParam.end-columnParam.start+1;


                        MediaColumn column = MediaColumnDb.getMediaColumn(detail.getEntityCode());
                        pageLayoutInfoDataDetail.setColumnCode(column.getColumnCode());
                        pageLayoutInfoDataDetail.setColumnType(column.getType());
                        pageLayoutInfoDataDetail.setName(column.getName());

                        //获取标签数据
                        if (detail.getEntityCode().equals("all_csbqlm1")) {
                            List<MediaTagLink> mediaTagLinks = MediaColumnContentDb.getMediaColumnTagLinkList(detail.getEntityCode());

                            List<TagLinkInfo> infos = new ArrayList<TagLinkInfo>();
                            for (MediaTagLink tagLink : mediaTagLinks) {
                                infos.add(new TagLinkInfo(tagLink));
                            }
                            pageLayoutInfoDataDetail.setTaglinkList(infos);
                        } else if (detail.getEntityCode().equals("all_gllm1")) {//攻略书单
                            List<MediaDigest> digests = MediaColumnContentDb.getMediaColumnDigestList(detail.getEntityCode(), dataNum);

                            List<HomePageDigest> infos = new ArrayList<HomePageDigest>();
                            for (MediaDigest digest : digests) {
                                HomePageDigest homePageDigest = new HomePageDigest();
                                homePageDigest.setDigest(new HomePageDigestInfo(digest));
                                infos.add(homePageDigest);
                            }
                            pageLayoutInfoDataDetail.setDigestList(infos);
                        } else if (detail.getEntityCode().equals("all_channel_column_test")) {//频道
                            List<com.dangdang.digital.meta.Channel> channels = MediaColumnContentDb.getChannelList(detail.getEntityCode(), dataNum);

                            List<ChannelList> channelInfos = new ArrayList<ChannelList>();
                            for (Channel channel : channels) {
                                ChannelList channelInfo = new ChannelList();
                                channelInfo.setChannelId(channel.getChannelId().toString());
                                channelInfo.setIsAllowMonthly(channel.getIsAllowMonthly() == null ? "0" : channel.getIsAllowMonthly().toString());
                                //channelInfo.setOwnerCustId(channel.getOwnerId().toString());
                                channelInfo.setTitle(channel.getTitle());

                                channelInfos.add(channelInfo);
                            }
                            pageLayoutInfoDataDetail.setChannelList(channelInfos);
                        }
                    }
                        break;
                    case "bookreview": {


                        //PageLayoutInfoData pageLayoutInfoData = new PageLayoutInfoData();
                       // pageLayoutInfo.setData(pageLayoutInfoData);

                       // PageLayoutInfoDataDetail pageLayoutInfoDataDetail = new PageLayoutInfoDataDetail();
                       // pageLayoutInfoData.setData(pageLayoutInfoDataDetail);

                        BarModule barModule = BarModuleDb.getHomePageBarModule("homeHotArticle");

                        BarModuleInfo expectBarModule = new BarModuleInfo();
                        expectBarModule.setType(barModule.getType());
                        expectBarModule.setBarModuleId(barModule.getBarModuleId());
                        expectBarModule.setModuleName(barModule.getModuleName());
                        expectBarModule.setShowNum(barModule.getShowNum());

                        //获取配置的首页精彩书评
                        List<BarModuleContent> postInfos = BarModuleContentDb.getPostInfoByLocation("homeHotArticle");

                        List<String> postIds = Util.getFields(postInfos, "contentId");
                        //获取帖子内容
                        List<MediaDigest> digests = MediaDigestDb.getMediaDigests(postIds);

                        List<HomePagePostInfos> homePagePostInfoses = new ArrayList<HomePagePostInfos>();
                        HomePagePostInfos homePagePostInfos = new HomePagePostInfos();
                        homePagePostInfoses.add(homePagePostInfos);


                        List<HomePagePostInfo> expectHomePageDigestInfos = new ArrayList<HomePagePostInfo>();
                        for (MediaDigest digest : digests) {
                            HomePagePostInfo digestInfo = new HomePagePostInfo();
                            digestInfo.setTitle(digest.getTitle());
                            digestInfo.setBarId(digest.getBarId());
                            digestInfo.setMediaDigestId(digest.getId());
                            //digestInfo.setType(digest.getType());
                            digestInfo.setPraiseNum(digest.getTopCnt());
                            digestInfo.setCommentNum(digest.getReviewCnt());

                            //判断是否点赞
                            if (login != null) {
                                digestInfo.setIsPraise(PraiseInfoDb.get(login.getCustId(), digest.getId().toString()) ? 1 : 0);
                            }

                            expectHomePageDigestInfos.add(digestInfo);
                        }
                        homePagePostInfos.setArticleContent(expectHomePageDigestInfos);
                        homePagePostInfos.setModule(expectBarModule);

                        pageLayoutInfoDataDetail.setBookReviewList(homePagePostInfoses);
                    }
                        break;
                    default:
                        throw new Exception("没有找到对应的模块类型");
                }
            }


            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(),expectPageLayoutDetailReponse,true));

        }
        super.dataVerify();
    }



}
