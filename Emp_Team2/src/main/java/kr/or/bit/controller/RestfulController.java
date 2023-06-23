package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.service.EmpService;

@RestController
@RequestMapping("/emp")
public class RestfulController {

	
	
	@Autowired
	private EmpService empService;
	
	
	@DeleteMapping(value = "{empno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEmp(@PathVariable int empno) {
	    int result = empService.deleteEmp(empno);

	    String message = (result == 1) ? "삭제성공" : "삭제실패";
	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_JSON)
	            .body("\"" + message + "\"");
	}
	

	
	
}
