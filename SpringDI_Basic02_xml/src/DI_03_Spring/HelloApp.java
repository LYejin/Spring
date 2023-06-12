package DI_03_Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		
		//인터페이스 (다형성) 느슨한 구조 ....
		//영문
		//MessageBean en = new MessageBean_eng();
		//en.sayHello("Hong");
		//한글
		//MessageBean kr = new MessageBean_kr();
		//kr.sayHello("Hong");
		
		//spring을 통해서 컨테이너(메모리)를 만들고 (메모리) 안에 객체 생성 , 조립
		
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_03_Spring/DI_03.xml");
		MessageBean messagebean = context.getBean("messagebean",MessageBean.class);
		messagebean.sayHello("hong");
		
	}

}
/*
요구사항
1. 한글버전 (hong) >> 안녕 hong
2. 영문버전 (hong) >> Hello hong

MessageBean_kr
MessageBean_eng

interface 다형성 설계
*/