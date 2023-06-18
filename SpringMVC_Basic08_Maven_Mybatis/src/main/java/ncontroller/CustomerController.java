package ncontroller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
	
	@RequestMapping("notice.htm")   //   /customer/notice.htm
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
		List<Notice> list = null;
		try {
				list = noticedao.getNotices(page, field, query);
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
		
		model.addAttribute("list", list); // 자동으로 notice.jsp forward ....
		
		return "customer/notice";
    	//   /WEB-INF/views/  +  customer/notice   + .jsp
	}
	
	//DAO 단  상세보기 데이터 가져오기 함수
	//public Notice getNotice(String seq)
	@RequestMapping("noticeDetail.htm") 
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
		
		return "customer/noticeDetail";
	}
	
	//@GetMapping   (화면) :  select
	//@PostMapping  (처리) :  insert
	//<a class="btn-write button" href="noticeReg.htm">글쓰기</a>
	
	@GetMapping(value="noticeReg.htm")  //  /customer/noticeReg.htm  >> 전송 >> GET
	public String noticeReg() {
		
		return "customer/noticeReg";
	}
	
	//form method="post" action="" 현재 주소창에 있는 주소
	///customer/noticeReg.htm  전송 >> POST
	
	@PostMapping(value="noticeReg.htm")  
	public String noticeReg(Notice n , HttpServletRequest request) {
		  
		
			/*
			 Notice >> DTO
			 private List<CommonsMultipartFile> files; 
			  
			 files[0] >> 1.jpg
			 files[1] >> 2.jpg 
			 */
		
			List<CommonsMultipartFile> files = n.getFiles();
			List<String> filenames = new ArrayList<String>(); //파일명들 관리
			
			if(files != null && files.size() > 0) { //1개라도 업로드 된 파일이 있다면
				for(CommonsMultipartFile multifile : files) {
					 	String filename =multifile.getOriginalFilename();
						String path = request.getServletContext().getRealPath("/customer/upload"); //배포된 서버 경로 
						String fpath = path + "\\" + filename;
						System.out.println(fpath);
						
					if(!filename.equals("")) { //실제 파일 업로드 
						FileOutputStream fs =null;
						try {
							     fs = new FileOutputStream(fpath);
							     fs.write(multifile.getBytes());
							     
							     //파일 이름 추출  (DB insert)
							     filenames.add(filename); 
							     //
							     
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
					}
				}
			}
				   
			
			//파일명 (DTO)
			//List<String> filenames = new ArrayList<String>();  
			//[0] > 1.jpg
			//[1] > 2.jpg
			n.setFileSrc(filenames.get(0));
			n.setFileSrc2(filenames.get(1));
			
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
	
	//글 수정하기 (화면이면서 데이터 처리) GET
	//noticeEdit.htm
	//글번호 받기와     (String seq , Model model) 사용
	//noticeDetail.jsp 아래부분 링크 수정하기
	//<a class="btn-edit button" href="noticeEdit.htm?seq=${notice.seq}">수정</a>
	//<a class="btn-del button" href="noticeDel.htm?seq=${notice.seq}">삭제</a>
	@GetMapping(value="noticeEdit.htm")  //   /customer/noticeEdit.htm
	public String noticeEdit(String seq,Model model) {
		
		Notice  notice = null;
		
		try {
			notice = noticedao.getNotice(seq);
		} catch (ClassNotFoundException e ) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
				
		model.addAttribute("notice", notice); 		
		
		return "customer/noticeEdit";
	
	}
	
	//form method="post"
	@PostMapping("noticeEdit.htm")
	public String noticeEdit(Notice n , HttpServletRequest request) {
		  //파일 업로드 가능
		
		List<CommonsMultipartFile> files = n.getFiles();
		List<String> filenames = new ArrayList<String>(); //파일명들 관리
		
		if(files != null && files.size() > 0) { //1개라도 업로드 된 파일이 있다면
			for(CommonsMultipartFile multifile : files) {
				 	String filename =multifile.getOriginalFilename();
					String path = request.getServletContext().getRealPath("/customer/upload"); //배포된 서버 경로 
					String fpath = path + "\\" + filename;
					System.out.println(fpath);
					
				if(!filename.equals("")) { //실제 파일 업로드 
					FileOutputStream fs =null;
					try {
						     fs = new FileOutputStream(fpath);
						     fs.write(multifile.getBytes());
						     
						     //파일 이름 추출  (DB insert)
						     filenames.add(filename); 
						     //
						     
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
				}
			}
		}
			   
		
		//파일명 (DTO)
		//List<String> filenames = new ArrayList<String>();  
		//[0] > 1.jpg
		//[1] > 2.jpg
		n.setFileSrc(filenames.get(0));
		n.setFileSrc2(filenames.get(1));
	
		try {
				noticedao.update(n);  //DB update
			} catch (Exception e) {
				e.printStackTrace();
			} 
	//처리가 끝나면 상세 페이지로 : redirect  글번호를 가지고 ....
	return "redirect:noticeDetail.htm?seq="+n.getSeq();    //서버에게 새 요청 ....
	}
}
