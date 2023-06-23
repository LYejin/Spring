package kr.or.bit.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dto.Emp;
import kr.or.bit.service.AjaxTableListService;
import kr.or.bit.service.EmpAddService;
import kr.or.bit.service.EmpChartService;
import kr.or.bit.service.EmpDataTableService;
import kr.or.bit.service.EmpDeleteService;
import kr.or.bit.service.EmpDeptNoListService;
import kr.or.bit.service.EmpDetailService;
import kr.or.bit.service.EmpEditOkService;
import kr.or.bit.service.EmpEditService;
import kr.or.bit.service.EmpFileUploadService;
import kr.or.bit.service.EmpJobListService;
import kr.or.bit.service.EmpListService;
import kr.or.bit.service.EmpLoginService;
import kr.or.bit.service.EmpLogoutService;
import kr.or.bit.service.EmpSearchService;
import kr.or.bit.service.EmpService;
import kr.or.bit.service.HieDeleteService;
import kr.or.bit.service.HieDetailService;
import kr.or.bit.service.HieEditService;
import kr.or.bit.service.HieEditServiceok;
import kr.or.bit.service.HieReWriteService;
import kr.or.bit.service.HieReWriteServiceok;
import kr.or.bit.service.HieReplyAddService;
import kr.or.bit.service.HieReplyDeleteService;
import kr.or.bit.service.HieReplyUpdateService;
import kr.or.bit.service.HieSearchService;
import kr.or.bit.service.HieTableService;
import kr.or.bit.service.HieWriteService;

@Controller
@RequestMapping("*.do")
public class FrontEmpController {

	private EmpService empservice;
	
	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}
	
	@GetMapping("/Main.do")
	public String main(Locale locale, Model model) {
		return "index";
	}
	
	@PostMapping("/login.do")
	public String login(String id, String password, HttpServletRequest request) {
		System.out.println("id : " +id + "password : "+ password);
		int loginResult = empservice.login(id, password);
		
		System.out.println("loginResult" + loginResult);
		if(loginResult > 0) {
			System.out.println("로그인 성공");
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			return "index";
		} else {
			System.out.println("로그인 실패");
			return "index";
		}
	}
	
	@GetMapping("/EmpTable.do")
	public String emplist(String ps, String cp, Model model) {
		System.out.println("ps : " + ps + "cp : " + cp);
		
		if (cp == null || cp.trim().equals("")) {
			cp = "1";
		}
		if (ps == null || ps.trim().equals("")) {
			ps = "5";
		}
		
		int pagesize = Integer.parseInt(ps);
		int cpage = Integer.parseInt(cp);
		int totalcount = empservice.totallistCount();
		int pagecount = 0;
		
		if (totalcount % pagesize == 0) { 
			pagecount = totalcount / pagesize;
		} else {
			pagecount = (totalcount / pagesize) + 1;
		}
		
		List<Emp> elist = empservice.list(cpage, pagesize);
		
		model.addAttribute("list", elist);
		model.addAttribute("pagesize", pagesize);
		model.addAttribute("pagecount", pagecount);
		model.addAttribute("cpage", cpage);
		return "EmpTable";
	}
	
	@GetMapping("/EmpWrite.do")
	public String empwrite() {
		return "EmpWrite";
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url_Command = requestURI.substring(contextPath.length());

		Action action = null;
		ActionForward forward = null;

		if (url_Command.equals("/Main.do")) { // 
			forward = new ActionForward();
			forward.setPath("/index.jsp");
			
		} else if (url_Command.equals("/login.do")) { // 
			action = new EmpLoginService();
			forward = action.execute(request, response);
			
		} else if(url_Command.equals("/logout.do")) { // 
			action = new EmpLogoutService();
			forward = action.execute(request, response);
			
		} else if(url_Command.equals("/EmpTable.do")) { //
			action = new EmpListService();
			forward = action.execute(request, response);
			
		} else if(url_Command.equals("/search.do")) { 
			action = new EmpSearchService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/EmpWrite.do")) { //
			forward = new ActionForward();
			String type = request.getParameter("type");
			request.setAttribute("type", type);
			forward.setPath("/WEB-INF/views/EmpWrite.jsp");
			
		}else if(url_Command.equals("/EmpWriteok.do")) { //
			action = new EmpAddService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/delete.do")) { 
			action = new EmpDeleteService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/detailView.do")) {
			action = new EmpDetailService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/update.do")) { //
			action = new EmpEditService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/updateok.do")) { //
			action = new EmpEditOkService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/chartView.do")) { //
			forward = new ActionForward();
			forward.setPath("/WEB-INF/views/chartView.jsp");
			
		}else if(url_Command.equals("/chartViewok.do")) { //
			action = new EmpChartService();
			forward = action.execute(request, response);
			
		}else if(url_Command.equals("/upload.do")) { //
			action = new EmpFileUploadService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/jobList.do")) { //
			action = new EmpJobListService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/deptNoList.do")) { //
			action = new EmpDeptNoListService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/dataTable.do")) { //화면 + 로직 )
			action = new EmpDataTableService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpTable.do")) { //화면 + 로직 )
			action = new HieTableService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpWrite.do")) { //화면 )
			forward = new ActionForward();
			String type = request.getParameter("type");
			request.setAttribute("type", type);
			forward.setPath("/WEB-INF/views/HieEmpWrite.jsp");
		}else if(url_Command.equals("/HieEmpWriteok.do")) { //
			action = new HieWriteService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpDetail.do")) { //
			action = new HieDetailService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpDelete.do")) { //
			action = new HieDeleteService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpEdit.do")) { //
			action = new HieEditService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpEditok.do")) { //
			action = new HieEditServiceok();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpReWrite.do")) { //
			action = new HieReWriteService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieEmpReWriteok.do")) { //
			action = new HieReWriteServiceok();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/HieSearch.do")) { //
			action = new HieSearchService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/replyDelete.do")) { //
			action = new HieReplyDeleteService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/replyAdd.do")) { //
			action = new HieReplyAddService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/replyUpdate.do")) { //
			action = new HieReplyUpdateService();
			forward = action.execute(request, response);
		}else if(url_Command.equals("/ajaxPaging.do")) { //화면 )
			forward = new ActionForward();
			forward.setPath("/WEB-INF/views/ajaxPaging.jsp");
		}else if(url_Command.equals("/ajaxTable.do")) { //화면 )
			action = new AjaxTableListService();
			forward = action.execute(request, response);
		}
		
		
	
	
		
		if (forward != null) {
			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
			dis.forward(request, response);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
