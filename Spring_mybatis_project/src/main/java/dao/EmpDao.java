package dao;

import java.sql.SQLException;
import java.util.List;

import vo.Emp;

public interface EmpDao {
		//전체 조회
		List<Emp> getEmps() throws ClassNotFoundException, SQLException;
		
		//조건 조회
		Emp getEmp(int empno) throws ClassNotFoundException, SQLException;
		
		// 삽입
		 int insert(Emp e) throws ClassNotFoundException, SQLException; 
		 
		// 수정
		int update(Emp e) throws ClassNotFoundException, SQLException;
	 
		// 삭제
		int delete(int empno) throws ClassNotFoundException, SQLException;
}
