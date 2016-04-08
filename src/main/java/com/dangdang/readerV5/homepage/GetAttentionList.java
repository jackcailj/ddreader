package com.dangdang.readerV5.homepage;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelSubUserDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.db.ucenter.UserInfoSql;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.GetAttentionListReponse;
import com.dangdang.ucenter.meta.BookFirend;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-3-23.
 */
public class GetAttentionList extends FixtureBase{

    ReponseV2<GetAttentionListReponse> reponseResult;


    public GetAttentionList(){}


    public ReponseV2<GetAttentionListReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetAttentionListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0) {
            String limit = StringUtils.isBlank(paramMap.get("pageSize"))?"10":""+Integer.parseInt(paramMap.get("pageSize"));
            SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String createDate="";
            if(org.apache.commons.lang3.StringUtils.isNotBlank(paramMap.get("createDateLong"))) {
                createDate = sdp.format(new Date(Long.parseLong(paramMap.get("createDateLong"))));
            }

            String date = (paramMap.get("act").equals("new")?">":"<")+(org.apache.commons.lang3.StringUtils.isNotBlank(createDate)?"'"+createDate +"'":"0");

            List<BookFirend> friends= UserInfoSql.getMyBookFriends(login.getCustId());

            List<String> activeUserIds = Util.getFields(friends,"activeUserId");
            List<String> passiveUserIds = Util.getFields(friends,"passiveUserId");
            List<Channel> subChannels = ChannelSubUserDb.getUserSubChannels(login.getCustId());
            List<String> channelUsersId= Util.getFields(subChannels,"custId");

            activeUserIds.addAll(passiveUserIds);
            activeUserIds.addAll(channelUsersId);
            List<MediaDigest> digests = MediaDigestDb.getAttendDigestList(login.getCustId(),activeUserIds, limit, date);

            List<String> exceptDigestIds= Util.getFields(digests,"id");
            List<String> returnDigestIds = Util.getFields(reponseResult.getData().getBookFriendMoments(),"digestId");

            dataVerifyManager.add(new ListVerify(returnDigestIds,exceptDigestIds,false).setVerifyContent("验证返回的帖子id是否正确"));
        }

        super.dataVerify();
    }
}
