package com.dangdang.readerV5.reponse;

public class UpdateResponse {
	String isCompatible;
	String isNew;
	Version version;
	public String getIsCompatible() {
		return isCompatible;
	}
	public void setIsCompatible(String isCompatible) {
		this.isCompatible = isCompatible;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}

}
