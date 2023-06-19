package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dao.NoticeDao;
import vo.Notice;

@Service
public class CustomerService {
	// CustomerService 는 SqlSessionTemplate 에 의존합니다
	// SqlSessionTemplate 구현하는 인터페이스 SqlSession

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 글목록보기 서비스
	public List<Notice> notices(String pg, String f, String q) {

		// 각 parameter 기본값 설정
		// default 값 설정
		int page = 1;
		String field = "TITLE";
		String query = "%%";

		if (pg != null && !pg.equals("")) {
			page = Integer.parseInt(pg);
		}

		if (f != null && !f.equals("")) {
			field = f;
		}

		if (q != null && !q.equals("")) {
			query = q;
		}

		// DAO 작업
		// DAO 작업 예외 throws ClassNotFoundException, SQLException
		List<Notice> list = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			list = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	// 글상세보기 서비스
	public Notice noticesDetail(String seq) {
		Notice notice = null;
		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notice;

	}

	public String noticeReg(Notice n, HttpServletRequest request) {

		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명들 관리

		if (files != null && files.size() > 0) { // 1개라도 업로드 된 파일이 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload"); // 배포된 서버 경로
				String fpath = path + "\\" + filename;
				System.out.println(fpath);

				if (!filename.equals("")) { // 실제 파일 업로드
					FileOutputStream fs = null;
					try {
						fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());

						// 파일 이름 추출 (DB insert)
						filenames.add(filename);
						//

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							fs.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));

		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			noticedao.insert(n); // DB insert
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:notice.htm";
	}

	public Notice noticeEdit(String seq) {

		Notice notice = null;

		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notice;

	}

	public String noticeEdit(Notice n, HttpServletRequest request) {
		// 파일 업로드 가능
		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); // 파일명들 관리

		if (files != null && files.size() > 0) { // 1개라도 업로드 된 파일이 있다면
			for (CommonsMultipartFile multifile : files) {
				String filename = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/customer/upload"); // 배포된 서버 경로
				String fpath = path + "\\" + filename;
				System.out.println(fpath);

				if (!filename.equals("")) { // 실제 파일 업로드
					FileOutputStream fs = null;
					try {
						fs = new FileOutputStream(fpath);
						fs.write(multifile.getBytes());

						// 파일 이름 추출 (DB insert)
						filenames.add(filename);
						//

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							fs.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));

		try {
			NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
			noticedao.update(n); // DB update
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 처리가 끝나면 상세 페이지로 : redirect 글번호를 가지고 ....
		return "redirect:noticeDetail.htm?seq=" + n.getSeq(); // 서버에게 새 요청 ....
	}

	public String noticeDel(String seq) {
		////////////////////////////////////////////////////////////
		NoticeDao noticedao = sqlsession.getMapper(NoticeDao.class);
		////////////////////////////////////////////////////////////
		try {
			noticedao.delete(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:notice.htm";
	}

	// 파일 다운로드 서비스 함수
	public void download(String p, String f, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String fname = new String(f.getBytes("euc-kr"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname + ";");

		String fullpath = request.getServletContext().getRealPath("/customer/" + p + "/" + f);
		System.out.println(fullpath);
		FileInputStream fin = new FileInputStream(fullpath);

		ServletOutputStream sout = response.getOutputStream();
		byte[] buf = new byte[1024]; // 전체를 다읽지 않고 1204byte씩 읽어서
		int size = 0;
		while ((size = fin.read(buf, 0, buf.length)) != -1) {
			sout.write(buf, 0, size);
		}
		fin.close();
		sout.close();
	}
}
