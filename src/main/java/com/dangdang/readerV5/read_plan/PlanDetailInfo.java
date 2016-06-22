package com.dangdang.readerV5.read_plan;

import com.dangdang.digital.meta.PlanDetail;

/**
 * Created by cailianjie on 2016-4-13.
 */
public class PlanDetailInfo extends PlanDetail {
    Double salePrice;

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
}
