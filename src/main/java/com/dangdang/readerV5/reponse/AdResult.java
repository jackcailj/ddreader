package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import com.dangdang.ddframework.dataverify.verify_annotation.Null;

public class AdResult {
	String addr_id;
	String addr_index;
	String city_id;
	String city_name;
	String country_id;
	String country_name;
	String cust_id;
	String identity_num;
	String is_valid_address;
	String pack_type;
	String pay_id;
	String province_id;
	@NotNull
	String province_name;
	String quarter_id;
	@NotNull
	String quarter_name;
	@NotNull
	String ship_address;
	String ship_date_type;
	String ship_mb;
	@NotNull
	String ship_name;
	@NotNull
	String ship_tel;
	String ship_type;
	@NotNull
	String ship_zip;
	String status;
	String town_id;
	String town_name;
	
	public String getAddr_id(){
		return addr_id;
	}
	public String getAddr_index(){
		return addr_index;
	}
	public String getCity_id(){
		return city_id;
	}
	public String getCity_name(){
		return city_name;
	}
	public String getCountry_id(){
		return country_id;
	}
	public String getCountry_name(){
		return country_name;		
	}
	public String getCust_id(){
		return cust_id;
	}
	public String getIdentity_num(){
		return identity_num;
	}
	public String getIs_valid_address(){
		return is_valid_address;
	}
	public String getPack_type(){
		return pack_type;
	}
	public String getPay_id(){
		return pay_id;
	}
	public String getProvince_id(){
		return province_id;
	}
	public String getProvince_name(){
		return province_name;
	}
	public String getQuarter_id(){
		return quarter_id;
	}
	public String getQuarter_name(){
		return quarter_name;
	}
	public String getShip_address(){
		return ship_address;
	}
	public String getShip_date_type(){
		return ship_date_type;
	}
	public String getShip_mb(){
		return ship_mb;
	}
	public String getShip_name(){
		return ship_name;
	}
	public String getShip_tel(){
		return ship_tel;
	}
	public String getShip_type(){
		return ship_type;
	}
	public String getShip_zip(){
		return ship_zip;
	}
	public String getStatus(){
		return status;
	}
	public String getTown_id(){
		return town_id;
	}
	public String getTown_name(){
		return town_name;
	}
	
	public void setAddr_id(String addr_id){
		this.addr_id = addr_id;
	}
	public void setAddr_index(String addr_index){
		this.addr_index = addr_index;
	}
	public void setCity_id(String city_id){
		this.city_id = city_id;
	}
	public void setCity_name(String city_name){
		this.city_name = city_name;
	}
	public void setCountry_id(String country_id){
		this.country_id = country_id;
	}
	public void setCountry_name(String country_name){
		this.country_name = country_name;		
	}
	public void setCust_id(String cust_id){
		this.cust_id = cust_id;
	}
	public void setIdentity_num(String identity_num){
		this.identity_num = identity_num;
	}
	public void setIs_valid_address(String is_valid_address){
		this.is_valid_address = is_valid_address;
	}
	public void setPack_type(String pack_type){
		this.pack_type = pack_type;
	}
	public void setPay_id(String pay_id){
		this.pay_id = pay_id;
	}
	public void setProvince_id(String province_id){
		this.province_id = province_id;
	}
	public void setProvince_name(String province_name){
		this.province_name = province_name;
	}
	public void setQuarter_id(String quarter_id){
		this.quarter_id = quarter_id;
	}
	public void setQuarter_name(String quarter_name){
		this.quarter_name = quarter_name;
	}
	public void setShip_address(String ship_address){
		this.ship_address = ship_address;
	}
	public void setShip_date_type(String ship_date_type){
		this.ship_date_type = ship_date_type;
	}
	public void setShip_mb(String ship_mb){
		this.ship_mb = ship_mb;
	}
	public void setShip_name(String ship_name){
		this.ship_name = ship_name;
	}
	public void setShip_tel(String ship_tel){
		this.ship_tel = ship_tel;
	}
	public void setShip_type(String ship_type){
		this.ship_type = ship_type;
	}
	public void setShip_zip(String ship_zip){
		this.ship_zip = ship_zip;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public void setTown_id(String town_id){
		this.town_id = town_id;
	}
	public void setTown_name(String town_name){
		this.town_name = town_name;
	}

}
