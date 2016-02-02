package com.dangdang.readerV5.reponse;

public class GetDigestDetailReponse {
	Integer canBeDel;
	DigestDetailChannel channel;
	Integer subscribe;
	public Integer getCanBeDel() {
		return canBeDel;
	}
	public void setCanBeDel(Integer canBeDel) {
		this.canBeDel = canBeDel;
	}
	public DigestDetailChannel getChannel() {
		return channel;
	}
	public void setChannel(DigestDetailChannel channel) {
		this.channel = channel;
	}
	public Integer getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
}
