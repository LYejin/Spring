package kr.or.bit.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bit.dto.Emp;
import kr.or.bit.service.EmpService;

@Controller
@RequestMapping("*.do")
public class FrontEmpController {

	private EmpService empservice;
	
	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}
	
	@GetMapping("/Main.do")
	public String main(Locale locale, Model model) {
		return "index";
	}
	
	@PostMapping("/login.do")
	public String login(String id, String password, HttpServletRequest request) {
		System.out.println("id : " +id + "password : "+ password);
		int loginResult = empservice.login(id, password);
		
		System.out.println("loginResult" + loginResult);
		if(loginResult > 0) {
			System.out.println("로그인 성공");
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			return "index";
		} else {
			System.out.println("로그인 실패");
			return "index";
		}
	}
	
	@GetMapping("/EmpTable.do")
	public String emplist(String ps, String cp, Model model) {
		System.out.println("ps : " + ps + "cp : " + cp);
		
		if (cp == null || cp.trim().equals("")) {
			cp = "1";
		}
		if (ps == null || ps.trim().equals("")) {
			ps = "5";
		}
		
		int pagesize = Integer.parseInt(ps);
		int cpage = Integer.parseInt(cp);
		int totalcount = empservice.totallistCount();
		int pagecount = 0;
		
		if (totalcount % pagesize == 0) { 
			pagecount = totalcount / pagesize;
		} else {
			pagecount = (totalcount / pagesize) + 1;
		}
		
		List<Emp> elist = empservice.list(cpage, pagesize);
		
		model.addAttribute("list", elist);
		model.addAttribute("pagesize", pagesize);
		model.addAttribute("pagecount", pagecount);
		model.addAttribute("cpage", cpage);
		return "EmpTable";
	}
	
	@GetMapping("/update.do")
	public String empEdit(@RequestParam("empno") Long empno, Model model) {
		
		Emp emp = empservice.detailList(empno);
		
		String type = (String) model.getAttribute("type");
		
		model.addAttribute("emp", emp);
		model.addAttribute("type", type);
		
		return "updateView";
	}
	
	@GetMapping("/EmpWrite.do")
	public String empwrite() {
		return "EmpWrite";
	}
	
	@PostMapping("/updateok.do")
	public String empEditOk(Emp emp, @RequestParam CommonsMultipartFile file, HttpServletRequest request, Model model) {
		String type = request.getParameter("type");
		emp.setSal(Long.parseLong(Long.toString(emp.getSal()).replaceAll(",", "")));
		emp.setComm(Long.parseLong(Long.toString(emp.getComm()).replaceAll(",", "")));
		model.addAttribute("type", type);
		System.out.println("filename : " + emp.getFilename());
		int result = -1;
		
		try {
			result = empservice.updateOkEmp(emp, file, request);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		String url = "";
		
		System.out.println("result : " + result);
		if (result > 0) {
			url = "redirect:EmpTable.do";
		} else {
			url = "redirect:update.do?empno=" + emp.getEmpno();
		}
		
		return url;
	}
}
