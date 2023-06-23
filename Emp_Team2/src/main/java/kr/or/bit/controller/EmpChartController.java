package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.dao.EmpDao;
import kr.or.bit.service.EmpService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RestController
@RequestMapping("/EmpChart.do")
public class EmpChartController {


	@Autowired
	private EmpService empService;
	
	
	
	@PostMapping
	public void empChart(HttpServletResponse response) throws IOException {
		System.out.println("empChart.do 진입");
		
    	int empTotal = empService.EmpTotal();
    	int empSalAvg = empService.EmpSalAvg();
    	int deptTotal = empService.DeptTotal();
    	int empJobTotal = empService.EmpJobTotal();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	JSONObject obj = new JSONObject();
		obj.put("empTotal", empTotal);
		obj.put("empSalAvg", empSalAvg);
		obj.put("deptTotal", deptTotal);
		obj.put("empJobTotal", empJobTotal);
		
		jsonArr.add(obj);
		
    	response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(jsonArr);
		
		
	}
	
	
}
