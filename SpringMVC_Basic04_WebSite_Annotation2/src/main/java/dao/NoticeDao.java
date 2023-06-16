package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.Notice;

//CRUD
public class NoticeDao {
	//�Խù� ����
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE "+field+" LIKE ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		
		// 3. ���
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int cnt = rs.getInt("cnt");
		
		rs.close();
		st.close();
		con.close();
		
		return cnt;
	}
	
	//��ü �Խù�
	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException
	{					
		
		//int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		//int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...
		
		int srow = 1 + (page-1)*5; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 5 + (page-1)*5; //15, 30, 45, 60, 75, ...
		
		String sql = "SELECT * FROM";
		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N)";
		sql += "WHERE NUM BETWEEN ? AND ?";
		// 0. ����̹� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, srow);
		st.setInt(3, erow);
		// 3. ���
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while(rs.next()){
			Notice n = new Notice();
			n.setSeq(rs.getString("seq"));
			n.setTitle(rs.getString("title"));
			n.setWriter(rs.getString("writer"));
			n.setRegdate(rs.getDate("regdate"));
			n.setHit(rs.getInt("hit"));
			n.setContent(rs.getString("content"));
			n.setFileSrc(rs.getString("fileSrc"));
			
			list.add(n);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	
	//�Խù� ����
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		// 2. ������ ���̽� ������ ���� ������ ���� �ڵ� �ۼ�
		String sql = "DELETE NOTICES WHERE SEQ=?";
		// 0. ����̹� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		PreparedStatement st = con.prepareStatement(sql);	
		st.setString(1, seq);
		
		int af = st.executeUpdate();
		
		return af;
	}
	
	//�Խù� ����
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		// 2. ������ ���̽��� �����ϱ� ���� ������ �����ͺ��̽� ������ ���� �ڵ带 �ۼ�
		String sql = "UPDATE NOTICES SET TITLE=?, CONTENT=?, FILESRC=? WHERE SEQ=?";
		// 0. ����̹� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getFileSrc());
		st.setString(4, notice.getSeq());		
		
		int af = st.executeUpdate();
		
		return af;
	}
	
	//�Խù� ��
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM NOTICES WHERE SEQ="+seq;
		// 0. ����̹� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		Statement st = con.createStatement();
		// 3. ���
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		
		Notice n = new Notice();
		n.setSeq(rs.getString("seq"));
		n.setTitle(rs.getString("title"));
		n.setWriter(rs.getString("writer"));
		n.setRegdate(rs.getDate("regdate"));
		n.setHit(rs.getInt("hit"));
		n.setContent(rs.getString("content"));
		n.setFileSrc(rs.getString("fileSrc"));
		
		rs.close();
		st.close();
		con.close();
		
		return n;
	}

	//�Խù� �Է�
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) VALUES( (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'bituser', SYSDATE, 0, ?)";
		// 0. ����̹� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 1. ����
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:49161:xe",
				"springuser", "1004");
		// 2. ����
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, n.getTitle());
		st.setString(2, n.getContent());
		st.setString(3, n.getFileSrc());
		
		int af = st.executeUpdate();
		
		st.close();
		con.close();
		
		return af;
	}
}
