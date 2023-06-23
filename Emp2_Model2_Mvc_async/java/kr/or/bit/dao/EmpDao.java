package kr.or.bit.dao;

import javax.sql.DataSource;

import kr.or.bit.dto.Emp;
import kr.or.bit.utils.DB_Close;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

public class EmpDao {

	private static EmpDao empDao;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int result;
	DataSource ds = null;

	public EmpDao() {
		try {
			Context context = new InitialContext(); 
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");// java:comp/env/ + name
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized EmpDao getInstance() {
		if (empDao == null) {
			empDao = new EmpDao();
		}
		return empDao;
	}
	

	public int login(String userId, String userPw) {
		Connection conn = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT pwd").append(" FROM adminlist").append(" WHERE userid = ?");
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("pwd").equals(userPw)) {
					return 1;
				} else {
					return 0;
				}
			}
			conn.close(); // 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("login SQLException error");
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);

		}
		return -1;
	}

	public int EmpTotal() { 
		String sql = "select count(*) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int EmpSalAvg() { 
		String sql = "select round(avg(sal),0) from emp";
		Connection conn = null;
		int avg = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				avg = rs.getInt(1);
			}
			conn.close(); // 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpSalAvg SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return avg;
	}

	public int DeptTotal() { 
		String sql = "select count(distinct deptno) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("占싸쇽옙占쏙옙占쏙옙 : " + total);
				total = rs.getInt(1);
			}

			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("DeptTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int EmpJobTotal() {
		String sql = "select count(distinct job) from emp";
		Connection conn = null;
		int total = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			System.out.println("占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 : " + total);
			conn.close(); 
		} catch (SQLException e) {
			System.err.println(e);
			System.err.println("EmpJobTotal SQLException error");
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
		}
		return total;
	}

	public int totallistCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		try {
			conn = ds.getConnection(); 
			String sql = "select count(*) cnt from emp";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalcount = rs.getInt("cnt");
			}
		} catch (Exception e) {

		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return totalcount;
	}

	public List<Emp> list(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Emp> list = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from  " + "(select rownum rn, empno, ename, job, mgr, hiredate, sal, comm, deptno, filename"
					+ "             from emp " +
													
					"             where rownum <= ?" + 
					") where rn >= ?"; // start row
			pstmt = conn.prepareStatement(sql);

			int start = cpage * pagesize - (pagesize - 1); // 1*5 -(5-1) = 1
			int end = cpage * pagesize; // 1 * 5 = 5

			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();
			list = new ArrayList<Emp>();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}
	
	public List<Emp> ajaxList(int cpage, int pagesize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Emp> list = null;
		try {
			conn = ds.getConnection();
			String sql = "select * from emp";
			pstmt = conn.prepareStatement(sql);

			/*
			 * int start = cpage * pagesize - (pagesize - 1); // 1*5 -(5-1) = 1 int end =
			 * cpage * pagesize; // 1 * 5 = 5
			 * 
			 * pstmt.setInt(1, end); pstmt.setInt(2, start);
			 */

			rs = pstmt.executeQuery();
			list = new ArrayList<Emp>();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				//emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return list;
	}

	public List<Emp> searchEmpno(String empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();

		try {
			conn = ds.getConnection();
			String sql = "select empno, ename, job, deptno, mgr, filename from emp where empno like ?";

			pstmt = conn.prepareStatement(sql);
			System.out.println("DAIO!! " + empno);
			pstmt.setString(1, "%" + empno + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Emp emp = new Emp();
				emp.setEmpno(rs.getLong(1));
				emp.setEname(rs.getString(2));
				emp.setJob(rs.getString(3));
				emp.setDeptno(rs.getLong(4));
				emp.setMgr(rs.getLong(5));
				emp.setFilename(rs.getString(6));
				list.add(emp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public List<Emp> searchEname(String ename) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();

		try {
			conn = ds.getConnection();
			String sql = "select empno, ename, job, deptno, mgr, filename from emp where ename like ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + ename + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Emp emp = new Emp();
				emp.setEmpno(rs.getLong(1));
				emp.setEname(rs.getString(2));
				emp.setJob(rs.getString(3));
				emp.setDeptno(rs.getLong(4));
				emp.setMgr(rs.getLong(5));
				emp.setFilename(rs.getString(6));
				list.add(emp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	//�궗�썝 �벑濡앺븯湲�
	public int insertEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename) {
		Connection conn = null;// 異붽�

		try {
			conn = ds.getConnection();

			System.out.println(hiredate);
			String sql = "insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno,filename) values(?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, empno);
			pstmt.setString(2, ename);
			pstmt.setString(3, job);
			pstmt.setLong(4, mgr);
			pstmt.setDate(5, transformDate(hiredate));
			pstmt.setLong(6, sal);
			pstmt.setLong(7, comm);
			pstmt.setLong(8, deptno);
			pstmt.setString(9, filename);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Insert : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			try {
				conn.close(); // 諛쏇솚�븯湲�
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// �궇吏쒓� yyyymmdd �삎�떇�쑝濡� �엯�젰�릺�뿀�쓣 寃쎌슦 Date濡� 蹂�寃쏀븯�뒗 硫붿꽌�뱶
	public Date transformDate(String date) {
		SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");

		// Date濡� 蹂�寃쏀븯湲� �쐞�빐�꽌�뒗 �궇吏� �삎�떇�쓣 yyyy-mm-dd濡� 蹂�寃쏀빐�빞 �븳�떎.
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

		java.util.Date tempDate = null;

		// �쁽�옱 yyyymmdd濡쒕맂 �궇吏� �삎�떇�쑝濡� java.util.Date媛앹껜瑜� 留뚮뱺�떎.
		try {
			tempDate = beforeFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// java.util.Date瑜� yyyy-mm-dd �삎�떇�쑝濡� 蹂�寃쏀븯�뿬 String濡� 諛섑솚�븳�떎.
		String transDate = afterFormat.format(tempDate);

		// 諛섑솚�맂 String 媛믪쓣 Date濡� 蹂�寃쏀븳�떎.
		Date d = Date.valueOf(transDate);

		return d;
	}
	
	//�궗�썝 �궘�젣�븯湲�
	public int deleteEmp(long empno) {
		//�씪諛섍쾶�떆�뙋 : �궘�젣 ...
		
		//怨꾩링�삎 寃뚯떆�뙋 : �떟湲� 
		/*
		 1. �썝蹂멸� (�떟湲��씠 �엳�뒗 寃쎌슦)
		 2. �썝蹂멸� (�떟湲��씠 �뾾�뒗 寃쎌슦) : 洹몃깷 �궘�젣
		 
		�썝蹂멸� (�떟湲��씠 �엳�뒗 寃쎌슦)
		case 1: �썝蹂멸��씠 �궘�젣�떆 �떟蹂�湲� �엳�쑝硫� �떎 �궘�젣 (媛숈� refer delete)
		case 2: (�꽕�씠踰�)�썝蹂멸�留� �궘�젣 -> �굹癒몄� 泥섎━ (�뀓�뒪�듃 �삎�깭 (�썝蹂멸� �궘�젣 �몴�떆) (step, depth)
		case 3: �궘�젣�떆 �궘�젣�릺�뿀�쓣 �몴�떆 ( 寃뚯떆�뙋 �꽕怨� (delok :�궘�젣�뿬遺� 而щ읆 :1) >> �궘�젣 : 0 >> update .. 0
		case 4: �궘�젣 紐삵븯寃� �븳�떎 (�떟湲��씠 �엳�쑝硫�) refer count > 1
		*/
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		try {
				conn = ds.getConnection();
				//鍮꾩씤利� ..
				//�궘�젣 > 鍮꾨쾲 
				//泥섎━ > 湲�踰덊샇 ,鍮꾨쾲
				
				//鍮꾨쾲寃�利�
				String sql_empno="select empno from emp where empno=?";
				
				//寃뚯떆湲� �궘�젣
				String sql_board="delete from emp where empno=?";
				
				pstmt = conn.prepareStatement(sql_empno);
				pstmt.setLong(1, empno);
				rs = pstmt.executeQuery();
				if(rs.next()) { //�궘�젣湲��� 議댁옱
					//�궗�슜�옄媛� �엯�젰�븳 鍮꾨쾲 , DB 鍮꾨쾲
						 //�떎 �궘�젣 泥섎━
						 //�듃�옖�옲�뀡 (�몮�떎 泥섎━ , �몮�떎 �떎�뙣)
						 //�몢媛쒕�� �븯�굹�쓽 �끉由ъ쟻 �떒�쐞
						 //JDBC : auto commit 
						 conn.setAutoCommit(false);//媛쒕컻�옄媛� rollback , commit 媛뺤젣
						 	
						 	//寃뚯떆湲� �궘�젣 (�썝蹂멸� , �떟湲�)
						 	pstmt = conn.prepareStatement(sql_board);
						 	pstmt.setLong(1,empno);
						 	row = pstmt.executeUpdate();
						 	
						 	if(row > 0) {
						 		conn.commit(); //�몢媛쒖쓽 delete �떎諛섏쁺
	
					 }else { //鍮꾨�踰덊샇媛� �씪移� �븯吏� �븡�뒗 寃쎌슦
						  row = -1;
					 }
				}else { //�궘�젣�븯�뒗 湲��씠 議댁옱�븯吏� �븡�뒗 寃쎌슦
					row = 0;					
				}
				
				
		} catch (Exception e) {
			//rollback 
			//�삁�쇅媛� 諛쒖깮�븯硫�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();//諛섑솚
			} catch (Exception e2) {
				
			}
		}
		return row;
	}
	
	
	public Emp detailList(Long empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Emp emp = new Emp();
		try {
			conn = ds.getConnection();
			String sql = "select * from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, empno);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString(("filename")));
			}

		} catch (Exception e) {
			System.out.println("�삤瑜� :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 諛섑솚
			} catch (Exception e2) {

			}
		}
		return emp;
	}
	
	public int updateOkEmp(long empno, String ename, String job, long mgr, String hiredate, long sal, long comm,
			long deptno, String filename) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();

			String sql = "update emp set ename = ?, job =?, mgr = ?, hiredate = ?, sal = ?,comm = ?, deptno = ?, filename = ? WHERE empno = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ename);
			pstmt.setString(2, job);
			pstmt.setLong(3, mgr);
			pstmt.setDate(4, transformDate(hiredate));
			pstmt.setLong(5, sal);
			pstmt.setLong(6, comm);
			pstmt.setLong(7, deptno);
			pstmt.setString(8, filename);
			pstmt.setLong(9, empno);
			
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Update : " + e.getMessage());
		} finally {
			try {
				pstmt.close();
				conn.close(); // 諛쏇솚�븯湲�
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	
	public List<Emp> chartList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Emp> list = new ArrayList<Emp>();
		
		try {
			conn = ds.getConnection();
			
			  String sql = "select job , avg(sal)*12 sar\r\n" + "from emp\r\n" +
			  "group by job\r\n" + "order by sar desc";
			 
//			String sql = "select * from vv2";
			pstmt = conn.prepareStatement(sql);

			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getLong("sar"));
				
				list.add(emp);
				System.out.println(emp);
			}
		} catch (Exception e) {
			System.out.println("�삤瑜� :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 諛섑솚
			} catch (Exception e2) {

			}
		}

		return list;

	}
	
	
	
	public List<Emp> jobList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();

		try {
			conn = ds.getConnection();
			String sql = "select distinct job from emp";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	public List<Emp> deptNoList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();

		try {
			conn = ds.getConnection();
			String sql = "select distinct deptno from emp order by deptno asc";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				DB_Close.close(rs);
				DB_Close.close(pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	
	
	public List<Emp> dataTablelist() {
		String sql = "select * from emp";
		List<Emp> list = new ArrayList<Emp>();
		Connection conn = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Emp emp = new Emp();
				emp.setDeptno(rs.getLong("deptno"));
				emp.setEmpno(rs.getLong("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setComm(rs.getLong("comm"));
				emp.setHiredate(rs.getDate("hiredate"));
				emp.setMgr(rs.getLong("mgr"));
				emp.setSal(rs.getLong("sal"));
				emp.setFilename(rs.getString("filename"));
				list.add(emp);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			System.err.println("selectList SQLException error");
		} finally {
	
			DB_Close.close(rs);
			DB_Close.close(pstmt);
		}
		return list;
	}
	
	public String empFilename(Long empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String filename = "";
		try {
			conn = ds.getConnection();
			String sql = "select filename from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, empno);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				filename = rs.getString("filename");
			}

		} catch (Exception e) {
			System.out.println("�삤瑜� :" + e.getMessage());
		} finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();// 諛섑솚
			} catch (Exception e2) {
				System.out.println("�삤瑜� :" + e2.getMessage());
			}
		}
		return filename;
	}
	
	
	
}
