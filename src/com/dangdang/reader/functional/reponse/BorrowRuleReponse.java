package com.dangdang.reader.functional.reponse;

import java.util.List;

public class BorrowRuleReponse {
	Account account;
	Integer price;
	List<Rules> rules;
	
	public List<Rules> getRules() {
		return rules;
	}

	public void setRules(List<Rules> rules) {
		this.rules = rules;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	
}
