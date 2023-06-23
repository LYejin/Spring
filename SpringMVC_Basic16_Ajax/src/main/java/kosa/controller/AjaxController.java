package kosa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import kosa.vo.Employee;
import kosa.vo.TestVO;

@Controller
public class AjaxController {

	//org.springframework.web.servlet.view.json.MappingJackson2JsonView
	//View 인터페이스 부모타입
	@Autowired
	private View jsonview;
	
	//command=AjaxTest&name=java&arr="+array
	@RequestMapping("json.kosa")
	public View jsonkosa(String command , String name , String [] arr , ModelMap map) {
		
		System.out.println("command : " + command);
		System.out.println("name : " + name);
		System.out.println("arr : " + arr);
		System.out.println("Arrays.toString() : " + Arrays.toString(arr));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("치킨");
		list.add("맥주");
		list.add("피자");
		
		map.addAttribute("menu", list); //View 단에 전달
		//{:}
		//{"menu":Array}
		
		return 	jsonview;	
		//<bean name="jsonview" 객체 와 동일한 이름)
		//map.addAttribute("menu", list); 자원을 자동으로 json객체로 변환 해서 클라이언트에게 비동기 전달
	}
	
	@RequestMapping("json2.kosa")
	public View jsonkosa(String command , String name , ModelMap map) {
		System.out.println("command : " + command);
		System.out.println("name : " + name);
		
		List<TestVO> list = new ArrayList<TestVO>(); //객체배열
		
		TestVO vo = new TestVO();
		vo.setBeer("라거");
		vo.setFood("골뱅이");
		
		list.add(vo);
		
		TestVO vo2 = new TestVO();
		vo2.setBeer("카스");
		vo2.setFood("치킨");
		
		list.add(vo2);
		
		
		map.addAttribute("menu", list);
		//{"menu":[{"beer":"라거","food":"골뱅이"},{}]}
		
		return jsonview;
	}
	
	//Employee 객체 2개 만들어서 List 형태로 반환하는 비동기 처리 함수 생성해서 테스트
	@RequestMapping("json3.kosa")
	public View jsonkosa(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
				
		Employee employee = new Employee();
		employee.setFirstname("kg");
		employee.setLastname("lim");
		employee.setEmail("i7027@naver.com");
		
		List<Employee> list = new ArrayList<>();
		list.add(employee);
		
		Employee employee2 = new Employee();
		employee2.setFirstname("h");
		employee2.setLastname("ong");
		employee2.setEmail("hong@naver.com");
		list.add(employee2);
		
		map.addAttribute("data", list);
		
		return jsonview;
	}
	
	
	@RequestMapping("json4.kosa")
	public void typeFunction(@RequestParam(value="aaa[]",required = false) String[] aaa,
			                 @RequestParam(value="bbb",required = false) String bbb ,
			                 String ccc) {
		
		for(String str : aaa) {
			System.out.println(str);
		}
		System.out.println(bbb);
		System.out.println(ccc); //문자열 (객체형태 문자열) >> 객체로 전환 
	}
	
	
	
}
