package com.dangdang.reader.functional.param.parse._enum;

/**
 * Excel中productIds字段解析
 * @author guohaiying
 *
 */
public enum ProductIdsEnum {
	userOverdueBorrowedBook("用户已过期的借阅本", 1), //用户已借阅的书，purchaseEbookVirtualPayment 接口参数productIds
	canBorrowBook("可借阅的书", 2),
	ValidBook("有效书籍", 3),
	XiaJiaBook("下架书籍", 4),
    Font("字体",5),
    Free("字体",6)
	;
	
	private String name;
	private int index;
	
	private ProductIdsEnum(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public static int getIndex(String name){
		for(ProductIdsEnum p : ProductIdsEnum.values()){
			if(p.getName().equals(name)){
				return p.index;
			}
		}
		return -1;
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}

}
