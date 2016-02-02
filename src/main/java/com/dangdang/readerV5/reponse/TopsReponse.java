package com.dangdang.readerV5.reponse;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-11-13.
 */
public class TopsReponse {
    List<Map<String, Object>> saleList;
    Integer total;

    public List<Map<String, Object>> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Map<String, Object>> saleList) {
        this.saleList = saleList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
