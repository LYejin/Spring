package service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.MemberDao;
import vo.Member;

@Service
public class MemberService {
	
	private SqlSession sqlsession;
	
	@Autowired
	public void setMemberdao(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	
	public String join(Member member) {
		////////////////////////////////////////////////////////////
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
		////////////////////////////////////////////////////////////
		try {
			memberdao.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/index.htm";
	}
}
