package kosa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.EmpService;
import vo.Emp;

@RestController	//@Controller + 함수(@ResponseBody)
public class AjaxController {
	
	private EmpService empservice;

	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}
	
	@RequestMapping("list.ajax")
	public List<vo.Emp> empList(){
		List<vo.Emp> list = empservice.getEmpAllList();
		return list;
	}
	
	@GetMapping("search.ajax")
	public List<vo.Emp> empSearch(String empno){
		Emp emp = empservice.getDetailEmp(empno);
		List<vo.Emp> list = new ArrayList<vo.Emp>();
		list.add(emp);
		return list;
	}
	
	@GetMapping("update.ajax")
	public Emp empEdit(String empno) {
		Emp emp = empservice.updateEmp(empno);
		return emp;
	}
	
	@PostMapping("update.ajax")
	public List<vo.Emp> empEdit(Emp emp) {
		empservice.updateEmp(emp);
		return empList();
	}
	
	@RequestMapping("delete.ajax")
	public List<vo.Emp> empDel(String empno){
		empservice.empDel(empno);
		return empList();
	}
	
	@RequestMapping("insert.ajax")
	public List<vo.Emp> empWrite(Emp emp){
		empservice.insertEmp(emp);
		return empList(); 
	}
	
	
	
}