package kr.or.bit.controller;

@RestController
public class AjaxController {
	
	@RequestMapping("empinsert.ajax")
	public Emp ajaxEmpAdd(@RequestBody Emp emp) {
		return emp;
	}
}
