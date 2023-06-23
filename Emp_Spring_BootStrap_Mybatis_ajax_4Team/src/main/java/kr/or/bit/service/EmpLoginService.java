package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.EmpDao;

public class EmpLoginService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		EmpDao empDao = EmpDao.getInstance();
		int loginResult = empDao.login(id, password);
		
		String msg = "";
		String url = "";
		if(loginResult > 0){
			msg = "성공";
			url = "Main.do";
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
		}else {
			msg = "실패";
			url = "Main.do";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/common/redirect.jsp");

		return forward;
	}

}
