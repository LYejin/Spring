package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import kr.or.bit.dto.Emp;

public interface EmpDao {
	int login(String userId, String userPw) throws ClassNotFoundException, SQLException;
	int EmpTotal() throws ClassNotFoundException, SQLException;
	int EmpSalAvg() throws ClassNotFoundException, SQLException;
	int DeptTotal() throws ClassNotFoundException, SQLException;
	int EmpJobTotal() throws ClassNotFoundException, SQLException;
	int totallistCount() throws ClassNotFoundException, SQLException;
	List<Emp> list(int cpage, int pagesize) throws ClassNotFoundException, SQLException;
	List<Emp> ajaxList(int cpage, int pagesize) throws ClassNotFoundException, SQLException;
	List<Emp> searchEmpno(String empno) throws ClassNotFoundException, SQLException;
	List<Emp> searchEname(String ename) throws ClassNotFoundException, SQLException;
	int insertEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm, long deptno,
			String filename) throws ClassNotFoundException, SQLException;
	Date transformDate(String date) throws ClassNotFoundException, SQLException;
	int deleteEmp(long empno) throws ClassNotFoundException, SQLException;
	Emp detailList(Long empno) throws ClassNotFoundException, SQLException;
	int updateOkEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm, long deptno,
			String filename) throws ClassNotFoundException, SQLException;
	List<Emp> chartList() throws ClassNotFoundException, SQLException;
	List<Emp> jobList() throws ClassNotFoundException, SQLException;
	List<Emp> deptNoList() throws ClassNotFoundException, SQLException;
	List<Emp> dataTablelist() throws ClassNotFoundException, SQLException;
	String empFilename(Long empno) throws ClassNotFoundException, SQLException;
}
