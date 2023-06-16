package com.model;

import lombok.Data;

//DB 테이블과 1:1 매핑되는 클래스
//create Article .... 

@Data
public class NewArticleCommand {
	private int parentId;
	private String title;
	private String content;
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
