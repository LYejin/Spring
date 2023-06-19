package ncontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.EmpDao;
import service.CustomerService;
import service.EmpService;
import vo.Emp;
import vo.Notice;

@Controller
public class EmpController {
	private EmpService empservice;
	
	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}
	
	//	게시판 화면 보여주기
	@RequestMapping("emplist.htm") 
	public String empList(Model model) {
		List<Emp> list = empservice.getEmps();
		model.addAttribute("list", list); 
		return "emp";
	}
	
	// 게시판 수정 페이지
	@RequestMapping("empDetail.htm") 
	public String emp(int empno, Model model) {
		Emp emp = empservice.getEmp(empno);
		model.addAttribute("emp", emp); 
		return "empDetail";
	}
	
	@GetMapping(value="empsearch.htm")
	public String getEmp(@RequestParam("empno") int empno, Model model) {
		Emp emp = empservice.getEmp(empno);
		model.addAttribute("emp", emp);
		return "empsearch";
	}
	
	@GetMapping(value="empinsert.htm")
	public String insert() {
		return "empInsert";
	}
	
	@PostMapping(value="empinsert.htm")
	public String insertEmp(Emp emp) {
		empservice.insertEmp(emp);
		return "redirect:emplist.htm";
	}
	
	@PostMapping(value="empDetail.htm")
	public String updateEmp(Emp emp) {
		empservice.updateEmp(emp);
		return "redirect:empDetail.htm?empno="+emp.getEmpno();
	}
	
	@GetMapping(value="empDel.htm")
	public String deleteEmp(int empno) {
		empservice.deleteEmp(empno);
		return "redirect:emplist.htm";
	}
}
