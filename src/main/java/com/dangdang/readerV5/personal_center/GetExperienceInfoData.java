package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.BaseComment.meta.CloudReadingInterval;
import com.dangdang.BaseComment.meta.CloudReadingTime;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CloudExperienceInfoDb;
import com.dangdang.db.comment.CloudReadingIntervalDb;
import com.dangdang.db.comment.CloudReadingTimeDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.enumeration.ExperienceEnum;
import com.dangdang.enumeration.StoreUpType;
import com.dangdang.readerV5.personal_center.cloud_sync_read.CloudExperienceInfoEx;
import com.dangdang.readerV5.reponse.ExperienceInfoData;
import com.dangdang.readerV5.reponse.GetExperienceInfoDataReponse;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by cailianjie on 2016-1-18.
 */
public class GetExperienceInfoData extends FixtureBase{

    ReponseV2<GetExperienceInfoDataReponse> reponseResult;


    public GetExperienceInfoData(){}

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetExperienceInfoDataReponse>>(){});
    }

    public ReponseV2<GetExperienceInfoDataReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            GetExperienceInfoDataReponse getExperienceInfoDataReponse = new GetExperienceInfoDataReponse();
            getExperienceInfoDataReponse.setExperienceData(new ExperienceInfoData());


            //阅读时段
            List<CloudReadingInterval> cloudReadingIntervals= CloudReadingIntervalDb.getCloudReadingInterval(login.getCustId());
            Map<String,Integer> habit=getExperienceInfoDataReponse.getExperienceData().getHabit();
            for(CloudReadingInterval cloudReadingInterval:cloudReadingIntervals){
                Calendar calendar =Calendar.getInstance();

                Date date = new Date(cloudReadingInterval.getStartTime());
                calendar.setTime(date);
                habit.put(""+date.getHours(),habit.get(""+""+date.getHours())+1 );
            }

            //阅读天数
            CloudExperienceInfoEx firstExperienceInfo = CloudExperienceInfoDb.getFirstCloudExperienceInfo(login.getCustId());
            if(firstExperienceInfo!=null) {
                Date now = new Date();
               // Date firstExperienceDate = new Date(firstExperienceInfo.getRecordTime());
                long datediff = (now.getTime() - firstExperienceInfo.getRecordTime()) / 1000;
                Long dayDiff = datediff / (24 * 3600);
                getExperienceInfoDataReponse.getExperienceData().setReadingDays(dayDiff.intValue());
            }
            //阅读时长
            CloudReadingTime cloudReadingTime = CloudReadingTimeDb.getReadingTime(login.getCustId());
            if(cloudReadingTime!=null){
                getExperienceInfoDataReponse.getExperienceData().setReadingTime(cloudReadingTime.getTotalReadingTime());
            }

            //发表的帖子数量
            List<CloudExperienceInfo> tieziList= CloudExperienceInfoDb.getCloudExperienceInfo(login.getCustId(), ExperienceEnum.FATIE.toString());
            getExperienceInfoDataReponse.getExperienceData().setReleaseArticleCount(tieziList.size());

            //发表的文章数量
            List<CloudExperienceInfo> articleList= CloudExperienceInfoDb.getCloudExperienceInfo(login.getCustId(), ExperienceEnum.PUBLISH_ARTICLE.toString());
            getExperienceInfoDataReponse.getExperienceData().setReleaseEssayCount(articleList.size());

            //发表的攻略数量
            List<CloudExperienceInfo> gongLvList= CloudExperienceInfoDb.getCloudExperienceInfo(login.getCustId(), ExperienceEnum.PUBLISH_GONGLUE.toString());
            getExperienceInfoDataReponse.getExperienceData().setReleaseStraegyCount(gongLvList.size());

            //获取分享数据
            List<CloudExperienceInfo> shareDatas= CloudExperienceInfoDb.getCloudExperienceInfo(login.getCustId(), ExperienceEnum.getShare());
            getExperienceInfoDataReponse.getExperienceData().setShareCount(shareDatas.size());

            //获取阅读完数据数量
            List<CloudExperienceInfo> readCompeletes= CloudExperienceInfoDb.getCloudExperienceInfo(login.getCustId(), ExperienceEnum.READ_COMPLETE_BOOK.toString());
            getExperienceInfoDataReponse.getExperienceData().setFinishReadCount(readCompeletes.size());


            dataVerifyManager.add( new ValueVerify<Object>(reponseResult.getData(),getExperienceInfoDataReponse,true));
        }
        super.dataVerify();
    }


}
