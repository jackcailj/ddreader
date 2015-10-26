package com.dangdang.param.parse;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.meta.Media;
import com.dangdang.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.bookshelf.GetUserBookList;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetBuyProductAndSaleIdParse implements IParamParse{


    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }


    /*
    获取产品productid
    参数：
        param：用，分割多个参数，格式BookType,ProductIdsEnum,数量
     */
    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        if(StringUtils.isNotBlank(param)){

            //用来处理混合情况，比如有效和无效的productId一起
            Integer number=Integer.parseInt(param);

            GetUserBookList getUserBookList = new GetUserBookList(((ILogin) VariableStore.get(VarKey.LOGIN)));
            getUserBookList.doWork();

            List mediaIds = new ArrayList();
            List<Media> medias = new ArrayList<Media>();
            int nMax=getUserBookList.getReponseResult().getData().getMediaList().size()>number?number:getUserBookList.getReponseResult().getData().getMediaList().size();
            for(int i=0;i<nMax;i++){
                Map<String,String> mediaMap=new HashMap<String, String>();
                mediaMap.put("productId", getUserBookList.getReponseResult().getData().getMediaList().get(i).getMediaId().toString());
                mediaMap.put("saleId", getUserBookList.getReponseResult().getData().getMediaList().get(i).getSaleId().toString());
                mediaIds.add(mediaMap);
                medias.add(getUserBookList.getReponseResult().getData().getMediaList().get(i));
            }

            paramMap.put(key, JSONObject.toJSONString(mediaIds));
            VariableStore.add(VarKey.MEDIAS, medias);

        }
        else{
            throw new Exception("GetProductAndSaleIdParse参数为空");
        }

    }
}
