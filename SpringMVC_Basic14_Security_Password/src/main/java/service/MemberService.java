package service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dao.MemberDao;
import vo.Member;

@Service
public class MemberService {
	
	private SqlSession sqlsession;
	
	@Autowired
	public void setMemberdao(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//회원가입 하기
	public String join(Member member) {
		////////////////////////////////////////////////////////////
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
		////////////////////////////////////////////////////////////
		
		member.setPwd(this.bCryptPasswordEncoder.encode(member.getPwd()));
		
		try {
			memberdao.insert(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/index.htm";
	}
}
