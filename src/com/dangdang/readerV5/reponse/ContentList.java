package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.bookbar.meta.Bar;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaDigest;

public class ContentList {
	List<Bar> barList;
	List<Channel> channelList;
	List<Media> mediaList;
	List<MediaDigest> digestList;
	
	public List<Bar> getBarList(){
		return barList;
	}
	public List<Channel> getChannelList(){
		return channelList;
	}
	public List<Media> getMediaList(){
		return mediaList;
	}
	public List<MediaDigest> getDigestList(){
		return digestList;
	}
	
	public void setBarList(List<Bar> barList){
		this.barList = barList;
	}	
	public void setChannelList(List<Channel> channelList){
		this.channelList = channelList;
	}
	public void setMediaList(List<Media> mediaList){
		this.mediaList = mediaList;
	}
	public void setDigestList(List<MediaDigest> digestList){
		this.digestList = digestList;
	}
}
