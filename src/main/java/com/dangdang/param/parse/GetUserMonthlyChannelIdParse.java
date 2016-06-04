package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.ChannelId;
import com.dangdang.enumeration.VarKey;
import com.dangdang.readerV5.channel.BuyMonthlyAuthority;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-3-23.
 */
public class GetUserMonthlyChannelIdParse implements IParamParse{
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


        if(channelStatus==ChannelId.IfAlreadyMonthly) {//查询用户是否有包月频道
            List<String> userMonthlyChannels = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custId);

            if(userMonthlyChannels.size()==0) {
                //没有包月频道，调用包月接口进行包月

                //获取有包月权限但没有包月的频道
                String channelId = ChannelDb.getMonthlyChannel(custId);

                BuyMonthlyAuthority buyMonthlyAuthority = new BuyMonthlyAuthority(login,channelId,false);
                buyMonthlyAuthority.doWorkAndVerify();

                paramMap.put(key,channelId);
            }
            else{
                paramMap.put(key,userMonthlyChannels.get(0));
            }
        }
        else if(channelStatus == ChannelId.NoOverdueIfAutoRenew ){
            String channelId = MediaMonthlyAuthorityDb.getAutoRenewChannel(custId,"0");

            paramMap.put(key,channelId);
        }

    }
}
