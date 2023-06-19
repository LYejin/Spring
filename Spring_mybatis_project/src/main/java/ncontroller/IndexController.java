package ncontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.EmpService;
import vo.Emp;

@Controller
public class IndexController {
	private EmpService empservice;
	
	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}

	@RequestMapping("/index.htm")
	public String index(Model model) {
		System.out.println("hiii");
		List<Emp> emplist = empservice.getEmps();
		model.addAttribute("emplist", emplist);
		return "emp";
		//   /WEB-INF/views/  +  index   + .jsp
	}
}
