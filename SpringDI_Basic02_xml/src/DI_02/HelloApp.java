package DI_02;

public class HelloApp {

	public static void main(String[] args) {
		
		//인터페이스 (다형성) 느슨한 구조 ....
		//영문
		MessageBean en = new MessageBean_eng();
		en.sayHello("Hong");
		//한글
		MessageBean kr = new MessageBean_kr();
		kr.sayHello("Hong");
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