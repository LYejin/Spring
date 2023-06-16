package com.model;

import lombok.Data;

//DB 상품 테이블 가정
//1:1 매핑

@Data
public class OrderItem {
	private int itemid;
	private int number;
	private String remark;
}
