package com.dangdang.reader.functional.param.model;

/**
 * Created by cailianjie on 2015-5-20.
 */
public class SmsCallbackParam extends ParamBase{
    String merc_id;
    String orderid;
    String userid;
    String app_orderid;
    String time;
    String rec_amount;
    String ch_type;
    String status;
    String sign;

    public String getMerc_id() {
        return merc_id;
    }

    public void setMerc_id(String merc_id) {
        this.merc_id = merc_id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getApp_orderid() {
        return app_orderid;
    }

    public void setApp_orderid(String app_orderid) {
        this.app_orderid = app_orderid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRec_amount() {
        return rec_amount;
    }

    public void setRec_amount(String rec_amount) {
        this.rec_amount = rec_amount;
    }

    public String getCh_type() {
        return ch_type;
    }

    public void setCh_type(String ch_type) {
        this.ch_type = ch_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
