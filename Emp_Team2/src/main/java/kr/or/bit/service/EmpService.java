package kr.or.bit.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.EmpDao;
import kr.or.bit.dto.Emp;

@Service
public class EmpService {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public int login(String id, String password) {
		
		int result = -1;
		
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		
		int loginResult = dao.login(id, password);
		
		System.out.println("**********************");
		System.out.println(loginResult);
		System.out.println("**********************");
		
		if(loginResult == Integer.parseInt(password)) {
			result = 1; 
		}else {
			result = 0;
		}
		
		return result;
	}
	
	public int EmpTotal() {
		int result = 0;
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		result = dao.EmpTotal();
		return result;
	}
	public int EmpSalAvg() {
		int result = 0;
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		result = dao.EmpSalAvg();
		return result;
	}
	public int DeptTotal() {
		int result = 0;
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		result = dao.DeptTotal();
		return result;
	}
	public int EmpJobTotal() {
		int result = 0;
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		result = dao.EmpJobTotal();
		return result;
	}
	
	public int totallistCount() {
		int result = 0;
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		result = dao.totallistCount();
		return result;
	}
	
	
	public List<Emp> list(int cpage, int pagesize){
		List<Emp> elist = new ArrayList<Emp>();
		
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		elist = dao.elist(cpage, pagesize);
		
		return elist;
	}
	/*
		// 비동기 사원검색 - 사원번호
	public List<Emp> searchEmpno(String empno);
	// 비동기 사원검색 - 사원이름
	public List<Emp> searchEname(String ename);
	 */
	
	public List<Emp> searchEmpno(int empno){
		List<Emp> elist = new ArrayList<Emp>();
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		elist = dao.searchEmpno(empno);
		
		return elist;
	}
	public List<Emp> searchEname(String ename){
		List<Emp> elist = new ArrayList<Emp>();
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		elist = dao.searchEname(ename);
		
		return elist;
	}
	
	
	public List<Emp> jobList(){
		List<Emp> elist = new ArrayList<Emp>();
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		elist = dao.jobList();
		
		return elist;
	}
	
	public List<Emp> deptNoList(){
		List<Emp> elist = new ArrayList<Emp>();
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		elist = dao.deptNoList();
		
		return elist;
	}
	
	
	public int insertEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename) {
		System.out.println("EmpService empno : " + empno);
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		int result = dao.insertEmp(empno, ename, job, mgr, hiredate, sal, comm, deptno, filename);
		
		return result;
	}
	
	//6월 23일 
	//사원 삭제
	public int deleteEmp(int empno) {
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		int result = dao.deleteEmp(empno);
		
		return result;
	}
	
	//사원 정보 한명 불러오기 - 사원 정보수정 페이지에서 사용
	public Emp detailList(Long empno) {
		EmpDao dao = sqlSession.getMapper(EmpDao.class);
		
		Emp emp = dao.detailList(empno);
	
		return emp;
	}
	
	
	
	
	
	
	
	
	
	
}
