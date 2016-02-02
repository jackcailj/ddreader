package com.dangdang.reader.functional.reponse;

import java.util.List;

public class NearbySearchResponse extends Data{

	List<NearbyList> nearbyList;
	
	public List<NearbyList> getNearbyList(){
		return nearbyList;
	}
	
	public void setNearbyList(List<NearbyList> nearbyList){
		this.nearbyList = nearbyList;
	}
}
