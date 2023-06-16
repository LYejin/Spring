package com.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/*
DB 에 테이블 (게시판 자료실)
create table photo(
	name
	age
	image >> 업로드한 파일의 이름 1.jpg, 2.jpg
	기타: 파일의 용량, 생성일, 수정일
)

실제 파일은 I/O 작업을 서버의 특정 폴더에 >> AWS >> s3 서버 > 파일서버

클라이언트 전송 서버 ---> public String form(Photo photo) --> 파일은 일반 String으로 받지 못한다.
(이름, 나이, 이미지, 파일)
 */

public class Photo {
	private String name;
	private int age;
	private String image;
	
	// 파일을 담을 수 있는 객체 제공
	//POINT
	private CommonsMultipartFile file; //업로드한 파일을 담아요 /name="file"이어서 commonsmultipartfile임 
	// <input type="file" name="file">
	// <form method="post" enctype="multipart/form-data">

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Photo [name=" + name + ", age=" + age + ", image=" + image + ", file=" + file + "]";
	}
	
	
}
