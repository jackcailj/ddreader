package com.dangdang.db.digital;

import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.digital.meta.MediaGiveDetail;
import com.dangdang.enumeration.PacketStatus;

import java.util.List;

/**
 * Created by cailianjie on 2015-10-12.
 */
public class MediaGiveDb {

    /*
    获取红包信息
     */
    public static MediaGive getMediaPacketInfo(String packetId) throws Exception {

        String selectString="select * from media_give where give_id='"+packetId+"'";
        return DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, MediaGive.class);

    }


    /*
    获取红包信息
     */
    public static MediaGive getMediaPacketInfo(String packetId,PacketStatus packetStatus) throws Exception {

        String selectString="select * from media_give where give_id='"+packetId+"' and status="+packetStatus.toString();
        return DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, MediaGive.class);

    }

    /*
    获取红包信息
     */
    public static MediaGive getMediaPacketInfo(PacketStatus packetStatus) throws Exception {

        String selectString="select * from media_give where  status="+packetStatus.toString()+" limit 1";
        return DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, MediaGive.class);

    }


    /*
   获取红包详情信息
    */
    public static List<MediaGiveDetail> getMediaPacketDetailInfo(String packetId) throws Exception {

        String selectString="select * from media_give_detail where packet_id='"+packetId+"';";
        return DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, MediaGiveDetail.class);

    }
}
