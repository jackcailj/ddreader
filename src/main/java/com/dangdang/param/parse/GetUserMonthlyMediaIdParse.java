package com.dangdang.param.parse;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaBooklistDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.meta.MediaBooklistDetail;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.ChannelId;
import com.dangdang.enumeration.VarKey;
import com.dangdang.readerV5.channel.BuyMonthlyAuthority;
import com.dangdang.readerV5.reponse.ChannelBookList;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-3-23.
 *获取用户包月频道里的书籍Id
 * 参数：1：频道状态，使用ChannleId枚举，包括已经包月、包月过期
 *      2：书籍Id状态，使用BookStatus枚举
 *      3: 书籍Id数量
 *      4：(可选)如果参数中需要传递ChanndelId，输入ChannelId参数名，如果ChannelId参数名：Incorrect，表示channelId与mediaId不匹配
 */
public class GetUserMonthlyMediaIdParse  implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        String[] params= ParamParse.parseParam(param);

        ILogin login = (ILogin) VariableStore.get(VarKey.LOGIN);
        String custId=login.getCustId();
        ChannelId channelStatus = ChannelId.valueOf(params[0]);
        BookStatus bookStatus = BookStatus.valueOf(params[1]);
        Integer num = Integer.parseInt(params[2]);

        String channelId="";

        if(channelStatus==ChannelId.IfAlreadyMonthly) {//查询用户是否有包月频道
            List<String> userMonthlyChannels = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId);

            if(userMonthlyChannels.size()==0) {
                //没有包月频道，调用包月接口进行包月

                //获取有包月权限但没有包月的频道
                channelId = ChannelDb.getMonthlyChannel(custId);

                BuyMonthlyAuthority buyMonthlyAuthority = new BuyMonthlyAuthority(login,channelId,false);
                buyMonthlyAuthority.doWork();
            }
            else{
                channelId = userMonthlyChannels.get(0);
            }
        }
        else if(channelStatus == ChannelId.NoOverdueIfAutoRenew ){
            channelId = MediaMonthlyAuthorityDb.getAutoRenewChannel(custId,"0");
        }

        //获取包月频道书单
        List<MediaBooklistDetail> booklistDetails = MediaBookListDetailDb.getMediaIdList(channelId);

        List<String> mediaIds =new ArrayList<String>();
        for(MediaBooklistDetail detail:booklistDetails){

            if(mediaIds.size()>=num){
                break;
            }

            mediaIds.add(detail.getMediaId().toString());
        }

        paramMap.put(key, StringUtils.join(mediaIds,","));

        //处理频道id参数
        String channelIdParamName="";
        if(params.length>=4){
            channelIdParamName=params[3];
            if(channelIdParamName.contains(":")){
                paramMap.put(channelIdParamName,"111222222");
            }
            else{
                paramMap.put(channelIdParamName,channelId);
            }
        }
    }
}
