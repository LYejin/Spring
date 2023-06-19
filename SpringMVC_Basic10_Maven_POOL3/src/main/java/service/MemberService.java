package service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dao.MemberDao;
import vo.Member;

public class MemberService {
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public String join() {
		return "joinus/join";
	}

	public String join(Member member) {
		try {
			MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
			memberdao.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/index.htm";
		
		//http://localhost:8090/SpringMvc/joinus/join.htm
		// "redirect:/index.htm";
		//http://localhost:8090/SpringMvc/index.htm
	}
}
