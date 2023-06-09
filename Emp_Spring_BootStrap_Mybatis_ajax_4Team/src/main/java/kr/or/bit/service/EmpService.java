package kr.or.bit.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bit.dao.EmpInterface;
import kr.or.bit.dto.Emp;

@Service
public class EmpService {
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public int login(String id, String pwd) {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		int result = -1;
		try {
			result = empdao.login(id, pwd);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("EmpLoginService : " + e.getMessage());
			result = 0;
		}
		return result;
	}
	
	public int totallistCount() {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		int result = -1;
		try {
			result = empdao.totallistCount();
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("totallistCountService : " + e.getMessage());
		}
		return result;
	}
	
	public List<Emp> list(int cpage, int pagesize) {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		List<Emp> emplist = null;
		System.out.println(cpage+ " " + pagesize);
		int start = cpage * pagesize - (pagesize - 1); // 1*5 -(5-1) = 1
		int end = cpage * pagesize;
		try {
			emplist = empdao.list(start, end);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("listService : " + e.getMessage());
		}
		return emplist;
	}
	
	public List<String> joblist() {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		List<String> joblist = null;
		try {
			joblist = empdao.jobList();
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("joblistService : " + e.getMessage());
		}
		return joblist;
	}
	
	public List<Long> deptnolist() {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		List<Long> deptnolist = null;
		try {
			deptnolist = empdao.deptnoList();
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("joblistService : " + e.getMessage());
		}
		return deptnolist;
	}
	
	public Emp detailList(long empno) {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		Emp detailList = null;
		try {
			detailList = empdao.detailList(empno);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("detailListService : " + e.getMessage());
		}
		return detailList;
	}
	
	public int deleteEmp(long empno) {
		EmpInterface empdao = sqlsession.getMapper(EmpInterface.class);
		int result = -1;
		try {
			result = empdao.deleteEmp(empno);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("deleteEmpService : " + e.getMessage());
		}
		return result;
	}
	
	public int insertEmp(Emp emp, HttpServletRequest request) {
		int result = 0;
		try {
			
//			CommonsMultipartFile file = emp.getFile();
//
//			String filename = file.getOriginalFilename();
//			String path = request.getServletContext().getRealPath("/upload"); // 배포된 서버 경로
//			String fpath = path + "\\" + filename;
//			System.out.println("fpath" + fpath);
//
			emp.setSal(Long.parseLong(Long.toString(emp.getSal()).replaceAll(",", "")));
			emp.setComm(Long.parseLong(Long.toString(emp.getComm()).replaceAll(",", "")));
//
//			if (!filename.equals("")) { // 실제 파일 업로드
//				FileOutputStream fs = null;
//				try {
//					fs = new FileOutputStream(fpath);
//					fs.write(file.getBytes());
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("empInsert 오류 : " + e.getMessage());
//				} finally {
//					try {
//						fs.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//		
//			emp.setFilename(filename);

			EmpInterface dao = sqlsession.getMapper(EmpInterface.class);
			
			try {
				result = dao.insertEmp(emp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateOkEmp(Emp emp, MultipartFile file, HttpServletRequest request) {
		int result = -1;
		emp.setSal(Long.parseLong(Long.toString(emp.getSal()).replaceAll(",", "")));
		emp.setComm(Long.parseLong(Long.toString(emp.getComm()).replaceAll(",", "")));
		try {
			//CommonsMultipartFile file = emp.getFile();
			
			System.out.println("file : " + file);
			
			String filename = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/upload"); // 배포된 서버 경로
			String fpath = path + "/" + filename;
			System.out.println(fpath);
			System.out.println(path);


			if (!filename.equals("")) { // 실제 파일 업로드
				FileOutputStream fs = null;
				System.out.println("파일 업로그 구문 실행");
				try {
					fs = new FileOutputStream(fpath);
					fs.write(file.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
				
			emp.setFilename(filename);

			EmpInterface dao = sqlsession.getMapper(EmpInterface.class);
			
			try {
				result = dao.updateOkEmp(emp);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("empUpdate 오류 : " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}
}
