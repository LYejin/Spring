package kr.or.kosa.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class IntroController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		System.out.println("IntroController 요청 실행 : handleRequest 자동 실행^^");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", "yejin");
		mav.setViewName("Intro");
		
		return mav;
	}
}
