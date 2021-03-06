package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.common.functional.login.LoginInfo;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.enumeration.BuyBookStatus;
import com.dangdang.enumeration.VarKey;
import com.dangdang.readerV5.personal_center.bookshelf.GetUserBookList;
import com.dangdang.readerV5.reponse.GetUserBookListReponse;
import com.dangdang.readerV5.reponse.UserBookMedia;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-10-10.
 */
public class GetBuyProductIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        //包含一个参数，为数量
        if(StringUtils.isNotBlank(param)){
            String[] params= ParamParse.parseParam(param);;
            BuyBookStatus buyBookStatus = BuyBookStatus.valueOf(params[0].trim());
            int num=Integer.parseInt(params[1].trim());

            GetUserBookList getUserBookList = new GetUserBookList((ILogin) VariableStore.get(VarKey.LOGIN));
            getUserBookList.doWork();

            ReponseV2<GetUserBookListReponse> reponse =getUserBookList.getReponseResult();


            //检查参数是否有用户名密码
            List<String> userBookIDList=null;
            if(params.length>2){
                String userParam = params[2].trim();
                String[] userInfo=userParam.split("\\\\");
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUserName(userInfo[0]);
                loginInfo.setPassWord(userInfo[1]);
                ILogin login =LoginManager.getLogin(loginInfo);

                GetUserBookList  userBookList = new GetUserBookList(login);
                userBookList.doWork();

                userBookIDList = Util.getFields(userBookList.getReponseResult().getData().getMediaList(),"mediaId");
            }

            List<String> mediaIds=new ArrayList<String>();
            for(UserBookMedia userBookMedia:reponse.getData().getMediaList()){
                if(buyBookStatus.getBookStatusString().contains(userBookMedia.getRelationType())
                        && userBookMedia.getAuthorityType()==buyBookStatus.getAuthorifyType()
                        &&(userBookIDList==null || (userBookIDList!=null && !userBookIDList.contains(userBookMedia.getMediaId().toString())))
                        ){
                    mediaIds.add(userBookMedia.getMediaId().toString());
                }

                if(mediaIds.size()==num){
                    break;
                }
            }



            paramMap.put(key,StringUtils.join(mediaIds,","));

        }
        else{
            throw new Exception("GetSendBook需要一个参数，代表获取的数量");
        }
    }
}
