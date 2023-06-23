package kr.or.bit.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.bit.dto.Emp;

@Service
public interface EmpDao {
	
	
	// 메인 페이지 차트기능 위한 4개
	public int EmpTotal();
	public int EmpSalAvg();
	public int DeptTotal();
	public int EmpJobTotal();
	
	// 로그인 기능 1개
	public int login(String id, String password);

	// 회원 관리 페이지 진입위한 2개
	public int totallistCount();
	public List<Emp> elist(int cpage,int pagesize);
	
	// 비동기 사원검색 - 사원번호
	public List<Emp> searchEmpno(int empno);
	// 비동기 사원검색 - 사원이름
	public List<Emp> searchEname(String ename);
	
	// 사원등록 페이지 - 직업목록
	public List<Emp> jobList();
	// 사원등록 페이지 - 부서번호목록
	public List<Emp> deptNoList();
	// 사원등록 페이지 - 사원 등록하기
	public int insertEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename);
	
	// 사원삭제 
	public int deleteEmp(int empno);
	
	//사원 정보 한명 불러오기 - 사원 정보수정 페이지에서 사용
	public Emp detailList(Long empno);
	
	//사원 정보 수정
	public int updateOkEMp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename);
	
	
}
