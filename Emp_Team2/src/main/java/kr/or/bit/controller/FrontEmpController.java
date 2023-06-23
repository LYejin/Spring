package kr.or.bit.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit.dto.Emp;
import kr.or.bit.service.EmpService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("*.do")
public class FrontEmpController {
	
	@Autowired
	private EmpService empService;
	
	
	// 메인페이지 이동
	@GetMapping("/Main.do")
	public String home(Locale locale, Model model) {
		return "index";
	}
	
	// 로그인 기능
	@PostMapping("/login.do")
	public String loginPost(String id, String password, HttpServletRequest request) {
		System.out.println("id : " + id + " password : " + password);
		
		int loginResult = empService.login(id, password);

		if(loginResult > 0){
			System.out.println("로그인 성공");
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			return "index";
		}else {
			System.out.println("로그인 실패");
			return "index";
		}
	}
	
	@GetMapping("/EmpTable.do")
	public String empTable(HttpServletRequest request, HttpServletResponse response) {
	    String ps = request.getParameter("ps"); // pagesize
	    String cp = request.getParameter("cp"); // current page

	    if (cp == null || cp.trim().equals("")) {
	        cp = "1";
	    }
	    if (ps == null || ps.trim().equals("")) {
	        ps = "5";
	    }

	    int pagesize = Integer.parseInt(ps);
	    int cpage = Integer.parseInt(cp);
	    int totalcount = empService.totallistCount();
	    int pagecount = (int) Math.ceil((double) totalcount / pagesize);

	    int offset = (cpage - 1) * pagesize;
	    int limit = pagesize;

	    List<Emp> elist = empService.list(offset, limit); // 페이지에 해당하는 게시물 가져오기

	    request.setAttribute("list", elist);
	    request.setAttribute("pagesize", pagesize);
	    request.setAttribute("pagecount", pagecount);
	    request.setAttribute("cpage", cpage);

	    return "EmpTable";
	}

	@GetMapping("EmpWrite.do")
	public String empWrite() {
		
		return "EmpWrite";
	}
	
	@PostMapping("EmpWriteok.do")
	public String empWriteOk(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    //String uploadpath = request.getSession().getServletContext().getRealPath("upload");
		String uploadpath = request.getSession().getServletContext().getRealPath("/resources/upload");
	    String type = request.getParameter("type");
	    int size = 1024 * 1024 * 10; // 10M
	    String context = request.getContextPath();
	    System.out.println("인서트 1 ");

	    // MultipartResolver를 사용하여 파일 업로드 처리
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile file = multipartRequest.getFile("fileName");

	    if (!file.isEmpty()) {
	        String originalFilename = file.getOriginalFilename();
	        String extension = FilenameUtils.getExtension(originalFilename);
	        String filename = System.currentTimeMillis() + "." + extension;
	        File destFile = new File(uploadpath, filename);
	        file.transferTo(destFile);

	        System.out.println("인서트 2 ");
	        String empno = multipartRequest.getParameter("empno");
	        System.out.println("empno : " + empno);
	        String ename = multipartRequest.getParameter("ename");
	        String job = multipartRequest.getParameter("job");
	        String mgr = multipartRequest.getParameter("mgr");
	        String hiredate = multipartRequest.getParameter("hiredate");
	        String sal = multipartRequest.getParameter("sal").replaceAll(",", "");
	        String comm = multipartRequest.getParameter("comm").replaceAll(",", "");
	        String deptno = multipartRequest.getParameter("deptno");

	        int result = 0;
	        try {
	        	System.out.println("인서트 3 ");
	            result = empService.insertEmp(Long.parseLong(empno), ename, job, Long.parseLong(mgr), hiredate,
	                    Long.parseLong(sal), Long.parseLong(comm), Long.parseLong(deptno), filename);
	            System.out.println("인서트 4 ");
	        } catch (Exception e) {
	            e.printStackTrace();
	            result = 0;
	        }

	        return "redirect:EmpTable.do";
	    } else {

	        return "redirect:EmpTable.do";
	    }
	}

	@GetMapping("update.do")
	public String updateView(@RequestParam("empno") Long empno, Model model) {
		
		Emp emp = empService.detailList(empno);
		
		String type = (String) model.getAttribute("type");
		
		model.addAttribute("emp", emp);
		model.addAttribute("type", type);
		
		return "updateView";
	}
	
	@PostMapping("updateok.do")
	public String updateok(HttpServletRequest request) throws IllegalStateException, IOException {
		String uploadpath = request.getSession().getServletContext().getRealPath("/resources/upload");
	    String type = request.getParameter("type");
	    int size = 1024 * 1024 * 10; // 10M
	    String context = request.getContextPath();
	    System.out.println("인서트 1 ");

	    // MultipartResolver를 사용하여 파일 업로드 처리
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile file = multipartRequest.getFile("fileName");

	    if (!file.isEmpty()) {
	        String originalFilename = file.getOriginalFilename();
	        String extension = FilenameUtils.getExtension(originalFilename);
	        String filename = System.currentTimeMillis() + "." + extension;
	        File destFile = new File(uploadpath, filename);
	        file.transferTo(destFile);

	        System.out.println("업데이트 2 ");
	        String empno = multipartRequest.getParameter("empno");
	        System.out.println("empno : " + empno);
	        String ename = multipartRequest.getParameter("ename");
	        String job = multipartRequest.getParameter("job");
	        String mgr = multipartRequest.getParameter("mgr");
	        String hiredate = multipartRequest.getParameter("hiredate");
	        String sal = multipartRequest.getParameter("sal").replaceAll(",", "");
	        String comm = multipartRequest.getParameter("comm").replaceAll(",", "");
	        String deptno = multipartRequest.getParameter("deptno");

	        int result = 0;
	        try {
	        	System.out.println("업데이트 3 ");
				//result = empService.updateOkEmp(Long.parseLong(empno), ename, job, Long.parseLong(mgr), hiredate,
				//		Long.parseLong(sal), Long.parseLong(comm), Long.parseLong(deptno), filename);
	            System.out.println("업데이트 4 ");
	        } catch (Exception e) {
	            e.printStackTrace();
	            result = 0;
	        }

	        return "redirect:EmpTable.do";
	    } else {

	        return "redirect:EmpTable.do";
	    }
	}
	
}
