package com.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/*
기존 방식
1. 요청 하나당 controller 한개가 필요하다
2. 게시판 (전체, 조건, 입력, 수정, 삭제) >> implements Controller 최소 5개
public class HelloController implements Controller {
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
	
3. 하나의 클래스가 여러개의 요청(글쓰기,목록보기,수정하기)을 처리하면 .... 요청단위 함수단위로 ...
	@Controller >> method 단위 매핑 >> 하나의 클래스 안에서 기능단위 함수 작성
	게시판
	목록보기 : public void list()
	글쓰기   : public void write()
	수정하기  : public void edit()
	
	@RestController
 */
@RestController
@Controller
public class HelloController {
	public HelloController() {
		System.out.println("HelloController 생성자 호출");
	}
	
	//	<a href="hello.do">hello.do 요청하기</a>
	@RequestMapping("/hello.do")
	public ModelAndView hello() {
		System.out.println("hello.do method call");
		ModelAndView mav = new ModelAndView();
		mav.addObject("greeting", getGreeting());
		mav.setViewName("Hello"); // /WEB-INF/views/Hello.jsp
		return mav;
	}
	
	private String getGreeting() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		String data="";
		if(hour >= 6 && hour <= 10) {
			data="학습시간";
		}else if(hour >= 11 && hour <= 13) {
			data="배고픈시간";
		}else if(hour >= 14 && hour <= 18) {
			data="졸려운시간";
		}else {
			data="go home";
		}
		return data;
		
	}
}
