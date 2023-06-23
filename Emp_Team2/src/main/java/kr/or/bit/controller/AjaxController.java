package kr.or.bit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dto.Emp;
import kr.or.bit.service.EmpService;
import net.sf.json.JSONArray;

@RestController
public class AjaxController {
	
	@Autowired
	private EmpService empService;
	
	@PostMapping("search.do")
	public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("search.do 진입");
		List<Emp> list = new ArrayList();
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String keyword = request.getParameter("keyword");
		
		System.out.println("empno*********************************");
		System.out.println(empno);
		System.out.println("ename*********************************");
		System.out.println(ename);
		
		if (empno != null && ename == null) {
			list = empService.searchEmpno(Integer.parseInt(empno));
			System.out.println("오류 찾기 컨트롤러 부분");
			
		} else {
			list = empService.searchEname(ename);
		}
		
		JSONArray jsonArr = JSONArray.fromObject(list); 

    	response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonArr);
		
	}
	
	@PostMapping("jobList.do")
	public void jobList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("jobList.do 진입");
		List<Emp> jobList = empService.jobList();
		JSONArray jsonArr = JSONArray.fromObject(jobList); 
    	response.setContentType("application/x-json; charset=UTF-8");
    	response.getWriter().print(jsonArr);
	}
	
	@PostMapping("deptNoList.do")
	public void deptNoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("deptNoList.do 진입");
		List<Emp> deptNoList = empService.deptNoList();
		System.out.println(deptNoList);
		JSONArray jsonArr = JSONArray.fromObject(deptNoList); 
    	response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonArr);
	}
	
	

	
	
}
