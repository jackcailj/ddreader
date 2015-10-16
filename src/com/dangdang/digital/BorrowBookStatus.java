package com.dangdang.digital;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by cailianjie on 2015-10-16.
 */
public enum BorrowBookStatus {
    VALID("借阅状态"),
    TIMEOUT("过期");

    String content="";


    BorrowBookStatus(String type){
        content=type;
    }

    public String getFilter(String borrowAuthorityAlias){
        borrowAuthorityAlias= StringUtils.isBlank(borrowAuthorityAlias)?borrowAuthorityAlias:borrowAuthorityAlias+".";
        Date date =new Date();
        if(this == VALID){
            return borrowAuthorityAlias+" deadline >= "+ date.getTime();
        }else if(this==TIMEOUT){
            return borrowAuthorityAlias+" deadline < "+ date.getTime();
        }

        return "";
    }


}
