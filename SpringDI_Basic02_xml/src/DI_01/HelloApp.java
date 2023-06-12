package DI_01;

public class HelloApp {

	public static void main(String[] args) {
		MessageBean messagebean = new MessageBean();
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