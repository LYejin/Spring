package main;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.bit.dto.User;
import kr.or.bit.service.SqlMapClient;

public class program {
	public static void main(String[] args) {
		//method scope
	    SqlSessionFactory sqlsession=SqlMapClient.getSqlSession();
	    
	    SqlSession session= sqlsession.openSession(); //연결 객체 생성
	    // bean 작업으로 위 과정 싹다 날아감
	    //session.selectOne("Emp.getone", arg1)

	    User user = (User)session.selectOne("Emp.getone", "ALLEN");
	    System.out.println(user.getEmpno());
	    System.out.println(user.getEname());
	    session.close();

	}

}



