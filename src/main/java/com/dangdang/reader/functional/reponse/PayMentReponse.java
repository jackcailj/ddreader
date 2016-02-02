package com.dangdang.reader.functional.reponse;

import java.util.List;

import com.dangdang.digital.meta.MediaActivityInfo;

public class PayMentReponse {
	List<MediaActivityInfo> payment;

	public List<MediaActivityInfo> getPayment() {
		return payment;
	}

	public void setPayment(List<MediaActivityInfo> payment) {
		this.payment = payment;
	}
}
