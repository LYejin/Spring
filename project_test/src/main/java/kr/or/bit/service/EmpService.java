package kr.or.bit.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.bit.dto.Emp;
import kr.or.bit.dao.EmpInterface;

@Service
public class EmpService {
	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public List<Emp> empList() {
		EmpInterface dao = sqlsession.getMapper(EmpInterface.class);
		List<Emp> emp = null;
		try {
			emp = dao.empList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	public Emp insertEmp(Emp emp, HttpServletRequest request) {
		try {
			CommonsMultipartFile file = emp.getFile();

			String filename = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/upload"); // 배포된 서버 경로
			String fpath = path + "\\" + filename;
			System.out.println(fpath);

			emp.setSal(Long.parseLong(Long.toString(emp.getSal()).replaceAll(",", "")));
			emp.setComm(Long.parseLong(Long.toString(emp.getComm()).replaceAll(",", "")));

			if (!filename.equals("")) { // 실제 파일 업로드
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(fpath);
					fs.write(file.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("empInsert 오류 : " + e.getMessage());
				} finally {
					try {
						fs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if (filename == null) {
				filename = "emp.jpg";
			} else {
				emp.setFilename(filename);
			}

			EmpInterface dao = sqlsession.getMapper(EmpInterface.class);
			
			try {
				dao.insertEmp(emp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return emp;
	}
	
	public Emp updateOkEmp(Emp emp, HttpServletRequest request) {
		try {
			CommonsMultipartFile file = emp.getFile();

			String filename = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/upload"); // 배포된 서버 경로
			String fpath = path + "\\" + filename;
			System.out.println(fpath);

			emp.setSal(Long.parseLong(Long.toString(emp.getSal()).replaceAll(",", "")));
			emp.setComm(Long.parseLong(Long.toString(emp.getComm()).replaceAll(",", "")));

			if (!filename.equals("")) { // 실제 파일 업로드
				FileOutputStream fs = null;
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

			if (filename == null) {
				filename = "emp.jpg";
			} else {
				emp.setFilename(filename);
			}

			EmpInterface dao = sqlsession.getMapper(EmpInterface.class);
			
			try {
				dao.updateOkEmp(emp);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("empUpdate 오류 : " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return emp;
	}
}
