package ncontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.CustomerService;
import service.EmpService;
import vo.Notice;

@Controller
public class EmpController {
	private EmpService empservice;
	
	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}

//	@RequestMapping("emp.htm") 
//	public String notices(String pg , String f , String q , Model model) {
//		List<Notice> list = customerservice.notices(pg, f, q);
//		model.addAttribute("list", list); 
//		return "customer/notice";
//	}
//	
//
//
//	@RequestMapping("noticeDetail.htm") 
//	public String noticesDetail(String seq , Model model) {
//		Notice notice = customerservice.noticesDetail(seq);				
//		model.addAttribute("notice", notice); 		
//		return "customer/noticeDetail";
//	}
//	
//	@GetMapping(value="noticeReg.htm")  
//	public String noticeReg() {
//		return "customer/noticeReg";
//	}
}
