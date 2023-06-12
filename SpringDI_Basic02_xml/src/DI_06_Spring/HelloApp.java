package DI_06_Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		
		//java 코드
		//Articleservice service = new Articleservice(new OracleDao());
//		Articleservice service = new Articleservice(new MySqlDao());
//		Article article = new Article();
//		service.write(article);
		
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_06_Spring/DI_06.xml");
		
		Articleservice service =  context.getBean("service", Articleservice.class);
		Article article = context.getBean("article", Article.class);
		
		service.write(article);
		
		/*
		  getBean()
		  1. 컨테이너 안에 있는 객체를 리턴  (new 가 아니예요)
		  2. return type Object (타입에 맞는 casting)
		  3. **스프링 컨테이너 안에 객체들은 default singleton
		  4. **예외적으로 getbean() 이 new 라는 할 수 도 있다 (거의 쓰지 않는다)
		 */
	}

}
