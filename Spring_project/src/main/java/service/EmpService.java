package service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.EmpDao;
import vo.Emp;

@Service
public class EmpService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public List<Emp> getEmps() {
		List<Emp> list = null;

		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			list = empdao.getEmpList();
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
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			emp = empdao.getEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	public void insertEmp(Emp emp) {
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			empdao.insertEmp(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateEmp(Emp emp) {
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			empdao.updateEmp(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmp(int empno) {
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			empdao.deleteEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
