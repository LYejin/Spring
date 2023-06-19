package ncontroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.CustomerService;
import vo.Notice;

@Controller
@RequestMapping("/customer/") 
public class CustomerController {
   
	private CustomerService customerservice;
	
	@Autowired
	public void setCustomerservice(CustomerService customerservice) {
		this.customerservice = customerservice;
	}

	@RequestMapping("notice.htm") 
	public String notices(String pg , String f , String q , Model model) {
		List<Notice> list = customerservice.notices(pg, f, q);
		model.addAttribute("list", list);
		return "customer/notice";
	}
	
	//DAO 단  상세보기 데이터 가져오기 함수
	//public Notice getNotice(String seq)
	@RequestMapping("noticeDetail.htm") 
	public String noticesDetail(String seq , Model model) {
		Notice  notice = customerservice.noticesDetail(seq, model);
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
		 customerservice.noticeReg(n, request);
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
		Notice notice = customerservice.noticeEdit(seq, model);
		model.addAttribute("notice", notice); 	
		return "customer/noticeEdit";
	}
	
	//form method="post"
	@PostMapping("noticeEdit.htm")
	public String noticeEdit(Notice n , HttpServletRequest request) {
		customerservice.noticeEdit(n, request);
		return "redirect:noticeDetail.htm?seq="+n.getSeq();    //서버에게 새 요청 ....
	}
	
	@GetMapping("noticeDel.htm")
	public String noticeDel(String seq) {
		return customerservice.noticeDel(seq);
	}
	
	//파일 다운로드
	@RequestMapping("download.htm")
	public void download(String p , String f , HttpServletRequest request , HttpServletResponse response) throws IOException {
		  customerservice.download(p, f, request, response);
	}
}
