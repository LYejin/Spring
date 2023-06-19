package dao;

import java.sql.SQLException;
import java.util.List;

import vo.Emp;

public interface EmpDao {
		//전체 조회
		List<Emp> getEmpList() throws ClassNotFoundException, SQLException;
		
		//조건 조회
		Emp getEmp(int empno) throws ClassNotFoundException, SQLException;
		
		// 삽입
		void insertEmp(Emp e) throws ClassNotFoundException, SQLException; 
		 
		// 수정
		void updateEmp(Emp e) throws ClassNotFoundException, SQLException;
	 
		// 삭제
		void deleteEmp(int empno) throws ClassNotFoundException, SQLException;
}
