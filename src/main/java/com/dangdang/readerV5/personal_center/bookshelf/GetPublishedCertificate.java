package com.dangdang.readerV5.personal_center.bookshelf;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class GetPublishedCertificate extends FixtureBase{

    public  GetPublishedCertificate(){}


    public  GetPublishedCertificate(ILogin login,String mediaId,String isFull,String refAction){
        setLogin(login);
        if(login!=null) {
            paramMap.put("token", login.getToken());
        }
        else{
            paramMap.put("token", "");
        }
        paramMap.put("mediaId",mediaId);
        paramMap.put("isFull",isFull);
        paramMap.put("refAction",refAction);
        paramMap.put("key","MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIJmES+jLfd54XNtju0wqJ4izofemTchm6x1PkpJCtR/FYEXYS2oxba32tjHcyhp0nl96ooe9TzA7WSeT4B6vNcCAwEAAQ==");


    }


}
