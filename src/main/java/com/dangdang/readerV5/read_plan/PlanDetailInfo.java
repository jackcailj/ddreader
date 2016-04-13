package com.dangdang.readerV5.read_plan;

import com.dangdang.digital.meta.PlanDetail;

/**
 * Created by cailianjie on 2016-4-13.
 */
public class PlanDetailInfo extends PlanDetail {
    Long salePrice;

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }
}
