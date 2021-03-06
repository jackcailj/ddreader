package com.dangdang.param.parse;

import com.dangdang.db.digital.MediaGiveDb;
import com.dangdang.digital.meta.MediaGive;
import com.dangdang.enumeration.PacketStatus;

import java.util.Map;

/**
 * Created by cailianjie on 2015-10-12.
 * 获取红包Id
 * 格式：#GetPacket#
 */
public class GetPacketParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        PacketStatus packetStatus = PacketStatus.valueOf(param);

        MediaGive mediaGive = MediaGiveDb.getMediaPacketInfo(packetStatus);
        paramMap.put(key,mediaGive.getGiveId());
    }
}
