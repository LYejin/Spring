package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.NewArticleCommand;
import com.service.ArticleService;

/*

클라이언트 요청....
1. 화면주세요 (글쓰기, 로그인 화면) : write.do
2. 처리해주세요 (글쓰기 입력에 대한 처리, 로그인 완료 처리) : writeok.do

요청주소가 : write.do 화면
요청주소가 : writeok.do 처리

spring
클라이언트 요청
요청을 method 단위로 처리
* 1개의 요청 주소로 화면, 처리 판단 > 근거 > 전송방식(GET,POST)
http:....../artice/newArticle.do
요청방식이
GET : 화면 > view 제공
POST : 서비스 처리 >> insert, update >> 화면 제공

 */
@Controller
@RequestMapping("/article/newArticle.do")
public class NewArticleController {
	
	//NewArticleController 업무수행을 위해서 ArticleService
	//has-a >> 연관
	private ArticleService articleService;
	
	@Autowired
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping //5.x.x
	public String form() { //화면
		System.out.println("GET 화면주세요");
		//public String form() 함수의 리턴 타입이 String 이면 Spring은 이것을 뷰의 주소로 인지한다.****
		//public ModelAndView form() >> ModelAndView return ...
		return "article/newArticleForm";
		// /WEB-INF/views/ +     + .jsp
	}
	
	@PostMapping //5.x.x
	public String submit(@ModelAttribute("Articledata") NewArticleCommand command) { //처리
		System.out.println("POST 처리해주세요");
		
		this.articleService.wrtieArticle(command);
		
		//ModelAndView mv = new ModelAndView();
		//mv.addObject("newArticleCommand", article); 
		//생략된게 자동으로 생성됨 ...
		
		//뷰 key : NewArticleCommand >> newArticleCommand 이게 key로 넘어감
		
		return "article/newArticleSubmitted";
	}
	
	/*
	 호랑이 담배 피던 시절에 했던 코드 ... HttpServletRequest
	 
	//@RequestMapping(method = RequestMethod.POST)
	@PostMapping //5.x.x
	public ModelAndView submit(HttpServletRequest request) { //처리
		System.out.println("POST 처리해주세요");
		NewArticleCommand article = new NewArticleCommand();
		article.setParentId(Integer.parseInt(request.getParameter("parentId")));
		article.setTitle(request.getParameter("title")); 
		article.setContent(request.getParameter("content"));
		
		this.articleService.wrtieArticle(article);
		ModelAndView mv = new ModelAndView();
		mv.addObject("newArticleCommand", article); // request.setAttribute(())
		mv.setViewName("article/newArticleSubmitted");
		
		return mv;
	}
	
	22
		@PostMapping //5.x.x
	public String submit(NewArticleCommand command) { //처리
		System.out.println("POST 처리해주세요");
		
		//parameter 과정을 없앰
		//1. 자동 DTO 객체 생성 : NewArticleCommand article = new NewArticleCommand();
		//2. 넘어온 parameter setter 함수를 통해서 자동 주입
		//3. NewArticleCommand command 객체 자동 IOC 컨테이너 안에 등록 : id=newArticleCommand
		// <bean id="newArticleCommand" class="..." 자동 생성 해줌
		this.articleService.wrtieArticle(command);
		ModelAndView mv = new ModelAndView();
		mv.addObject("newArticleCommand", command); // request.setAttribute(())
		mv.setViewName("article/newArticleSubmitted");
		
		return mv;
	}
	*/
	/*
	2. Spring에서 parameter DTO 객체로 받기
	2.1 자동화 >> 선행조건 >> input 태그의 name값이 DTO 객체의 memberfield 명과 동일
	 */
}
