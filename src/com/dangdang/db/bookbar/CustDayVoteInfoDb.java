package com.dangdang.db.bookbar;

import com.dangdang.bookbar.meta.AuditionAuthor;
import com.dangdang.bookbar.meta.CustDayVoteInfo;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class CustDayVoteInfoDb {

/*
获取每日获奖名单
 */
    public static List<CustDayVoteInfo> getCustDayVoteList() throws Exception {
        Date now =new Date();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        String nowString= simpleDateFormat.format(now);
        String selectString ="select * from cust_day_vote_info where vote_info_date = '"+ nowString+"' and lottery_type!=0 order by lottery_date DESC limit 100";
        List<CustDayVoteInfo> custDayVoteInfos = DbUtil.selectList(Config.BOOKBARDBConfig,selectString,CustDayVoteInfo.class);

        return custDayVoteInfos;
    }


    public static CustDayVoteInfo getTodayPrize(String custId) throws Exception {
        Date now =new Date();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        String nowString= simpleDateFormat.format(now);
        String selectString ="select * from cust_day_vote_info where vote_info_date = '"+ nowString+"' and lottery_type!=0 and cust_id="+custId;
        CustDayVoteInfo custDayVoteInfo = DbUtil.selectOne(Config.BOOKBARDBConfig,selectString,CustDayVoteInfo.class);

        return custDayVoteInfo;
    }

}
