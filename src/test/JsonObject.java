package test;

import java.util.List;

public class JsonObject<T> {
	String name;
	T list;
	
	public T getList() {
		return list;
	}
	public void setList(T list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
