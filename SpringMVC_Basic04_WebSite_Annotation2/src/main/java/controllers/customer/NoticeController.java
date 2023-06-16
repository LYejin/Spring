package controllers.customer;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dao.NoticeDao;
import vo.Notice;

@Controller
@RequestMapping("/customer/")
public class NoticeController{
	private NoticeDao noticedao;
	
	public NoticeController() {
		 System.out.println("[NoticeController]");
	}
	
	@Autowired
	public void setNoticsdao(NoticeDao noticsdao) {
		this.noticedao = noticsdao;
	}
	
	@RequestMapping("notice.do")
	public String notices(@RequestParam(value="pg", defaultValue="1") int page,
			@RequestParam(value="f", defaultValue="TITLE") String field, 
			@RequestParam(value="p", defaultValue="%%") String query, 
			Model model) throws ClassNotFoundException, SQLException  {
		
		// Model model >> 데이터 저장 >>> Model 구현하는 객체 자동 만들어서 줄게 >> 너 그냥 써 공짜야(ModelAndView에서 Model만)
		// HttpServletRequest request >> 생성된 request 객체의 주소 할당 ....
		
		//DAO 작업 : 예외 throws ClassNotFoundException, SQLException
		List<Notice> noticedata = this.noticedao.getNotices(page, field, query);
		model.addAttribute("noticedata", noticedata); // 자동으로 notice.jsp forward
		
		//list View 전달 (저장 >> 전달)
		//1. public ModelAndView ....
		//	 mv.addObject("list",list)
		//	 mv.setViewName("notice.jsp")
		// 	 return mv;
		
		return "/notice";
	}
	
	@RequestMapping("noticeDetail.do")
	public String noticeDetail(@RequestParam(value="seq", defaultValue="1") String seq, Model model) throws ClassNotFoundException, SQLException {
		Notice noticeDetailData = this.noticedao.getNotice(seq);
		model.addAttribute("noticeDetailData", noticeDetailData);
		return "/noticeDetail";
	}

}
