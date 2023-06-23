package kosa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kosa.vo.Emp;

@RestController //@Controller + 함수(@ResponseBody)
public class AjaxController {
	
	
	@RequestMapping("restcon.ajax")
	public List<Emp> ajaxResponseBody(){
		
		List<Emp> list = new ArrayList<Emp>();
		Emp e = new Emp();
		e.setEmpno(9999);
		e.setEname("홍길동");
		e.setJob("IT");
		e.setSal(1000);

		Emp e2 = new Emp();
		e2.setEmpno(1111);
		e2.setEname("김유신");
		e2.setJob("IT");
		e2.setSal(1000);
		
		
		list.add(e);
		list.add(e2);
		
		return list;
	}	
	
	@RequestMapping("view.ajax")
	public String ViewPage() {
		System.out.println("view.ajax");
		return "view.ajax 문자열이 반환";
	}
}
