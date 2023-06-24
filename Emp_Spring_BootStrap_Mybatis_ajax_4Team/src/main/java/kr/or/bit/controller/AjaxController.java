package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dto.Emp;
import kr.or.bit.service.EmpService;

@RestController
@RequestMapping("/emp")
public class AjaxController {
	
	public EmpService empservice;
	
	@Autowired
	public void setAjaxController(EmpService empservice) {
		this.empservice = empservice;
	}
	
//	@GetMapping
//	public ResponseEntity<String> select(@RequestBody Emp emp, HttpServletRequest request) {
//		try {
//			System.out.println("emplist 실행");
//			System.out.println(emp.toString());
//			empservice.empList();
//			return new ResponseEntity<String>("emplist sucess", HttpStatus.OK);
//		} catch (Exception e) {
//			e.getStackTrace();
//			return new ResponseEntity<String>("emplist fail", HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@PostMapping("/empInsert")
	public ResponseEntity<String> insert(@RequestBody Emp emp, HttpServletRequest request) {
		try {
			System.out.println("insert 실행");
			System.out.println(emp.toString());
			empservice.insertEmp(emp, request);
			return new ResponseEntity<String>("insert sucess", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("insert fail", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/empDelete/{empno}")
	public ResponseEntity<String> delete(@PathVariable("empno") int empno) {
		try {
			System.out.println("delete 실행");
			empservice.deleteEmp(empno);
			return new ResponseEntity<String>("delete sucess", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("delete fail", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/jobList")
	public ResponseEntity<List<String>> jobList() {
		List<String> list = new ArrayList<String>();
		try {
			System.out.println("jobList 실행");
			list = empservice.joblist();
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<String>>(list, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/deptnoList")
	public ResponseEntity<List<Long>> deptnoList() {
		List<Long> list = new ArrayList<Long>();
		try {
			System.out.println("deptnoList 실행");
			list = empservice.deptnolist();
			return new ResponseEntity<List<Long>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Long>>(list, HttpStatus.BAD_REQUEST);
		}
	}
}
