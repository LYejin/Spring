package kr.or.bit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.Emp;
import net.sf.json.JSONArray;

public class EmpSearchService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
			EmpDao empDao = EmpDao.getInstance();
			List<Emp> list = new ArrayList<Emp>();
			String empno = request.getParameter("empno");
			String ename = request.getParameter("ename");
			String keyword = request.getParameter("keyword");

			if (empno != null && ename == null) {
				list = empDao.searchEmpno(empno);
			} else {
				list = empDao.searchEname(ename);
			}
			

			JSONArray jsonArr = JSONArray.fromObject(list); 
		

	    	response.setContentType("application/x-json; charset=UTF-8");
			try {
				response.getWriter().print(jsonArr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		

	}

}