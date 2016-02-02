package com.dangdang.db.digital.im;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.GetMyGiveListData;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-9.
 */
public class IMDB {

    /*
    获取我的已赠图书列表
     */
    public static List<GetMyGiveListData> getMyGiveList(String custId) throws Exception {
        String selectString="select m.author_penname as authorName,m.title,m.sale_id as saleId,m.media_id as mediaId,m.descs as 'desc',UNIX_TIMESTAMP(mgd.get_date)*1000 as getDate from media_give mg\n" +
                "left join media_give_detail mgd on mg.give_id=mgd.packet_id left join media m on mgd.media_id=m.media_id where mg.give_cust_id="+custId+" and mgd.status =1 order by mgd.give_detail_id desc limit 10";

        return DbUtil.selectList(Config.YCDBConfig,selectString,GetMyGiveListData.class);
    }


    /*
   获取我的已收图书列表
    */
    public static List<GetMyGiveListData> getMyReceiveList(String custId) throws Exception {
        String selectString="    select\n" +
                "        m.author_penname as authorName,\n" +
                "        m.title,\n" +
                "        m.sale_id as saleId,\n" +
                "        m.media_id as mediaId,\n" +
                //"        m.descs as 'desc',\n" +
                "        UNIX_TIMESTAMP(mgd.get_date)*1000 as getDate \n" +
                "    from\n" +
                "        media_give_detail mgd \n" +
                "    left join\n" +
                "        media m \n" +
                "            on mgd.media_id=m.media_id \n" +
                "    where\n" +
                "        mgd.receive_cust_id= \n" +custId+
                "    order by\n" +
                "        mgd.give_detail_id desc limit 10";

        return DbUtil.selectList(Config.YCDBConfig,selectString,GetMyGiveListData.class);
    }
}
