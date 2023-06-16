package com.controller;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model.Photo;

@Controller
@RequestMapping("/image/upload.do")
public class ImageController {
	
	@GetMapping
	public String form() {
		return "image/image"; //파일 첨부 할 수 있는 view 제공
	}
	
	@PostMapping
	public String submit(Photo photo, HttpServletRequest request) { 
		
		/*
		1. Photo DTO 타입으로 데이터 받기
		1.1 자동화 : name 속성값이 Photo 타입클래스의 member field 명과 동일
		2. public String submit(Photo photo) 내부적으로 ...  
		   >> Photo photo = new Photo();
		   >> photo.setName("홍길동");
		   >> photo.setAge(20);
		   >> photo.setImage() >> 자동 주입 안되요 >> 수동으로 >> 가공 CommonsMultipartFile 추출(이름)
		   >> photo.setFile(CommonsMultipartFile file) 파일 자동으로 들어와요
		   
		 */
		
		System.out.println(photo.toString());
		CommonsMultipartFile imagefile = photo.getFile();
		System.out.println("imagefile.getName()" + imagefile.getName());
		System.out.println("imagefile.getContentType()" + imagefile.getContentType());
		System.out.println("imagefile.getOriginalFilename()" + imagefile.getOriginalFilename());
		// 왜 필요? - 덮어쓰겠다? 1.jpg, 1.1.jpg로 생성
		System.out.println("imagefile.getBytes().length" + imagefile.getBytes().length);
		
		//POINT 파일명 추출
		photo.setImage(imagefile.getOriginalFilename());
		
		//upload
		//자동화 -> 라이브러리 사용하기 : cos.jar(무료), 상용 업로드(덱스트 업로드)
		
		String filename = imagefile.getOriginalFilename();
		String path = request.getServletContext().getRealPath("/upload"); //배포된 서버의 경로
		String fpath = path + "\\" + filename; //c:\\temp\\a.jpg
		System.out.println(fpath);
		///Users/yejin/Documents/Douzone/Spring/SpringLabs/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMVC_Basic03_Annotation/upload\다람쥐.jpeg
		//실제 배포된 경로
		//덮어씀
		
		FileOutputStream fs = null;
		
		try {
			fs = new FileOutputStream(fpath); // 파일생성 >> 1.jpg 생성
			fs.write(imagefile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		//요기까지 작업 서버에 특정 폴더에 : 파일생성
		
		//Next DB 작업
		//DAO  >>  게시판 insert
		
		return "image/image";
	}
}
