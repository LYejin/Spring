package service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import dao.EmpDao;
import dao.NoticeDao;
import vo.Emp;

public class EmpService {
	
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public List<Emp> getEmps() {
		List<Emp> list = null;
		
		try {
			////////////////////////////////////////////////////////////
			EmpDao empdao= sqlsession.getMapper(EmpDao.class);
			////////////////////////////////////////////////////////////
			list = empdao.getEmps();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Emp getEmp(int empno) {
		Emp emp = null;
		
		try {
			////////////////////////////////////////////////////////////
			EmpDao empdao= sqlsession.getMapper(EmpDao.class);
			////////////////////////////////////////////////////////////
			emp = empdao.getEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	public String getEmp(Emp emp) {
		try {
			////////////////////////////////////////////////////////////
			EmpDao empdao= sqlsession.getMapper(EmpDao.class);
			////////////////////////////////////////////////////////////
			empdao.insert(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/emp.htm";
	}
}
