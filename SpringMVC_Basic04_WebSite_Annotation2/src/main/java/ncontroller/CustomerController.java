package ncontroller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.NoticeDao;
import vo.Notice;

@Controller
@RequestMapping("/customer/")  //부분 경로 ..... 긴 경우
public class CustomerController {
   
	//CustomerController 는 noticeDao 의존합니다 
	private NoticeDao noticedao;

	@Autowired
	public void setNoticedao(NoticeDao noticedao) {
		this.noticedao = noticedao;
	}
   /*
	1. method안에서 return type  [String] 리턴값이 뷰의 주소
	2. public ModelAndView notices ...    >  ModelAndView 객체 생성  > 데이터 , 뷰 설정 > return 
	3. public String notices (Model model) { 함수가 실행시 내부적으로 Model객체의 주소가 들어온다  }
   */
	
	//DAO 단 전체 게시물 데이터 가져오기 함수
	//public List<Notice> getNotices(int page, String field, String query)
	//함수는 요청을 받아서 게시물 전체보기....
	
	@RequestMapping("notice.do")   //   /customer/notice.htm
	public String notices(String pg , String f , String q , Model model) {
		
		// Model model  >> 데이터 저장 >> Model 구현하는 객체 자동 만들어서 줄게 >> 너 그냥 써
		//HttpServletRequest request   >> 생성된 request 객체의 주소 할당....
		
		
		//각 parameter 기본값 설정
		//default 값 설정
		int page = 1;
		String field="TITLE";
		String query = "%%";
		
		if(pg != null   && ! pg.equals("")) {
			page  = Integer.parseInt(pg);
		}
		
		if(f != null   && ! f.equals("")) {
			field = f;
		}
		
		if(q != null   && ! q.equals("")) {
			query  = q;
		}
		
		//DAO 작업
		//DAO 작업  예외 throws ClassNotFoundException, SQLException
		List<Notice> noticedata = null;
		try {
			noticedata = noticedao.getNotices(page, field, query);
		} catch (ClassNotFoundException e) {
					e.printStackTrace();
		} catch (SQLException e) {
					e.printStackTrace();
		}
		
		//list View 전달 (저장 >> 전달)
		//1. public ModelAndView .... 
		//   mv.addObject("list",list)
		//   mv.setViewName("notice.jsp")
		//   return mv
		
		model.addAttribute("noticedata", noticedata); // 자동으로 notice.jsp forward ....
		
		return "/notice";
	}
	
	//DAO 단  상세보기 데이터 가져오기 함수
	//public Notice getNotice(String seq)
	@RequestMapping("noticeDetail.do") 
	public String noticesDetail(String seq , Model model) {
		
		Notice  notice = null;
		
		try {
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e ) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
				
		model.addAttribute("notice", notice); 		
		
		return "/noticeDetail";
	}
	
	//@GetMapping   (화면) :  select
	//@PostMapping  (처리) :  insert
	//<a class="btn-write button" href="noticeReg.htm">글쓰기</a>
	
	@GetMapping(value="noticeReg.do")  //  /customer/noticeReg.htm  >> 전송 >> GET
	public String noticeReg() {
		return "/noticeReg";
	}
	
	//form method="post" action="" 현재 주소창에 있는 주소
	///customer/noticeReg.htm  전송 >> POST
	
	@PostMapping(value="noticeReg.do")  
	public String noticeReg(Notice n, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		 	String filename =n.getFile().getOriginalFilename();
			String path = request.getServletContext().getRealPath("/customer/upload"); //배포된 서버 경로 
			String fpath = path + "\\" + filename;
			System.out.println(fpath);
			
			FileOutputStream fs =null;
			try {
				     fs = new FileOutputStream(fpath);
				     fs.write(n.getFile().getBytes());
				     
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				 try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//파일명 (DTO)
			n.setFileSrc(filename);
			
			try {
					noticedao.insert(n);  //DB insert
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			//insert 나 update 하고 나면 ...(F5 누르면 계속 글이 ..Write)
			//리스트 (location.href    or   redirect )
			//서버에게 새로운 요청 ...목록보기
			//String :   redirect:notice.htm   
			//Servlet , jsp  :   location.href  or   response.sendRedirect 
			
			
		  return "redirect:notice.htm";
	}
	
	@GetMapping(value="noticeEdit.do") // /cusomer/noticeEdit.htm
	public String noticeEdit(String seq, Model model) {
		Notice notice = null;
		try {
				notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e) {
					e.printStackTrace();
		} catch (SQLException e) {
					e.printStackTrace();
		}
		
		//list View 전달 (저장 >> 전달)
		//1. public ModelAndView .... 
		//   mv.addObject("list",list)
		//   mv.setViewName("notice.jsp")
		//   return mv
		
		model.addAttribute("notice", notice); // 자동으로 notice.jsp forward ....
		
		return "/noticeEdit";
	}
	
	@PostMapping("noticeEdit.do")
	public String noticeEdit(Notice n) {
		//추후 파일처리
		
		return "redirect:noticeDetail.do?seq=1";
	}
}
