package com.dangdang.param.parse;

import com.dangdang.authority.meta.BorrowAuthority;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.authority.BorrowAuthorityDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.ddframework.util.Util;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.enumeration.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-10-16.
 * 获取可以借阅的书籍Id，返回meidaId，多个以逗号分开
 * 调用格式：#GetCanBorrowBookId#BookType，BookStatus，1 and 123
 */
public class GetCanBorrowBookIdParse implements IParamParse{
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

            /*GetBorrowAuthorityList getBorrowAuthorityList =new GetBorrowAuthorityList((ILogin) VariableStore.get(VarKey.LOGIN));
            getBorrowAuthorityList.doWork();
            List<String> productIds = Util.getFields(getBorrowAuthorityList.getReponseResult().getData().getBorrowList(),"productId");*/

            List<BorrowAuthority> borrowAuthorities = BorrowAuthorityDb.getAllBorrowMedias(((ILogin) VariableStore.get(VarKey.LOGIN)).getCustId());
            List<String> productIds = Util.getFields(borrowAuthorities,"productId");

            List<Media> medias = MediaDb.getCanBorrowMedia(bookType, bookStatus, number,productIds);

            List<String> mediaIds = new ArrayList<String>();
            //int nMax=medias.size()>number?number:medias.size();
            for(Media media:medias){
                //没有借阅过的书籍加入
                if(!productIds.contains(media.getProductId().toString())) {
                    mediaIds.add(media.getMediaId().toString());
                }
            }

            paramMap.put(key, StringUtils.join(mediaIds,","));


        }
        else{
            throw new Exception("GetCanBorrowBookParse参数为空");
        }

    }
}
