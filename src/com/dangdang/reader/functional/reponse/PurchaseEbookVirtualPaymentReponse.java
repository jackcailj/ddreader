package com.dangdang.reader.functional.reponse;

import java.util.List;

public class PurchaseEbookVirtualPaymentReponse {
	List<MobileEbookInfo> mobileEbooks;
	List<Long> productIds;
	Long borrowDuration;
	public List<MobileEbookInfo> getMobileEbooks() {
		return mobileEbooks;
	}
	public void setMobileEbooks(List<MobileEbookInfo> mobileEbooks) {
		this.mobileEbooks = mobileEbooks;
	}
	public List<Long> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
	public Long getBorrowDuration(){
		return borrowDuration;
	}
	public void setBorrowDuration(Long borrowDuration){
		this.borrowDuration = borrowDuration;
	}
}
