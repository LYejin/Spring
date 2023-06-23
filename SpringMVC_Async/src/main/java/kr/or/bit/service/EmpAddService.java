package kr.or.bit.service;


import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.Emp;

public class EmpAddService {
	
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public void empInsert(Emp emp, HttpServletRequest request)  {
		
		String uploadpath = request.getSession().getServletContext().getRealPath("upload");
		
		String type = request.getParameter("type");
		
		int size = 1024*1024*10; //10M 네이버 계산기
		String context = request.getContextPath();
		//output, input을 만들지 않아도됨, 좋음!!
		MultipartRequest multi = new MultipartRequest(
				request, //기존에 있는  request 객체의 주소값 
				uploadpath, //실 저장 경로 (배포경로)
				size, //10M
				"UTF-8",
				new DefaultFileRenamePolicy() //파일 중복(upload 폴더 안에:a.jpg -> a_1.jpg(업로드 파일 변경) )
		);
		
		try {
			
		String empno = multi.getParameter("empno");
		String ename = multi.getParameter("ename");
		String job = multi.getParameter("job");
		String mgr = multi.getParameter("mgr");
		String hiredate = multi.getParameter("hiredate");
		sal.set multi.getParameter("sal").replaceAll(",", "");
		String comm = multi.getParameter("comm").replaceAll(",", "");
		
		
		
		Enumeration filenames = multi.getFileNames();
		
		String file = (String)filenames.nextElement();
		String filename = multi.getFilesystemName(file);
		
		if(filename == null) {
			filename = "emp.jpg";
		}
		//String orifilename = multi.getOriginalFileName(file);
		
		EmpDao dao = sqlsession.getMapper(EmpDao.class);
		int result = 0;
		try {
			result = dao.insertEmp(Long.parseLong(empno), ename, job, Long.parseLong(mgr), hiredate,
					Long.parseLong(sal), Long.parseLong(comm), Long.parseLong(deptno), filename);
		} catch(Exception e) {
			e.printStackTrace();
			result = 0;
		}
		
		List jobList = dao.jobList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return emp;
	}
}
