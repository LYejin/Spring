package kr.or.kosa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login/loginForm.do")
	public String login() {
		logger.info("Welcome Login Form");
		return "login/loginForm";
	}
	
	
	@GetMapping("/login/accessDenied.do")
	public String accessDenied() {
		logger.info("Welcome access Denied");
		return "login/accessDenied";
	}
}


