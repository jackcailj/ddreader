package com.dangdang.param.parse;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.db.digital.BookStatus;
import com.dangdang.db.digital.BookType;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.param.parse._enum.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetCanBorrowBookParse implements IParamParse{


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
            String[] params = ParamParse.parseParam(param);;
            BookType bookType = BookType.valueOf(params[0]);
            BookStatus bookStatus= BookStatus.valueOf(params[1]);

            //用来处理混合情况，比如有效和无效的productId一起
            String[] numberSplit =ParamParse.parseParam(params[2], ParamParse.AND);
            Integer number=Integer.parseInt(numberSplit[0].trim());


            List<Media> medias = MediaDb.getCanBorrowMedia(bookType,bookStatus,number);

            List mediaIds = new ArrayList();
            int nMax=medias.size()>number?number:medias.size();
            for(int i=0;i<nMax;i++){
                Map<String,String> mediaMap=new HashMap<String, String>();
                mediaMap.put("productId", medias.get(i).getMediaId().toString());
                mediaMap.put("saleId", medias.get(i).getSaleId().toString());
                mediaIds.add(mediaMap);
                medias.add(medias.get(i));
            }

            paramMap.put(key, JSONObject.toJSONString(mediaIds));
            VariableStore.add(VarKey.MEDIAS, medias);

        }
        else{
            throw new Exception("GetCanBorrowBookParse参数为空");
        }

    }
}
