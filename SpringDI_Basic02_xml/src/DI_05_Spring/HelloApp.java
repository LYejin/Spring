package DI_05_Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		/*
		MyBean mybean = new MyBean();
		MyBean mybean2 = new MyBean("hong");
		MyBean mybean3 = new MyBean();
		
		System.out.println(mybean + " , " + mybean2 + " , " + mybean3);
		
		Singleton single = Singleton.getInstance();
		Singleton single2 = Singleton.getInstance();
		Singleton single3 = Singleton.getInstance();
		
		System.out.println(single + " , " + single2 + " , " + single3);
		*/
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_05_Spring/DI_05.xml");
		MyBean m =  context.getBean("mybean",MyBean.class);
		MyBean m2 =  context.getBean("mybean",MyBean.class);
		MyBean m3 =  context.getBean("mybean",MyBean.class);
		System.out.println(m);
		System.out.println(m2);
		System.out.println(m3);
		//DI_05_Spring.MyBean@589838eb
		//DI_05_Spring.MyBean@589838eb
		//DI_05_Spring.MyBean@589838eb
		
		Singleton single =  context.getBean("single",Singleton.class);
		Singleton single2 =  context.getBean("single",Singleton.class);
		
		System.out.println(single  + " , " + single2);
		
		/*
		  getBean()
		  1. 컨테이너 안에 있는 객체를 리턴  (new 가 아니예요)
		  2. return type Object (타입에 맞는 casting)
		  3. **스프링 컨테이너 안에 객체들은 default singleton
		  4. **예외적으로 getbean() 이 new 라는 할 수 도 있다 (거의 쓰지 않는다)
		    
		  
		 
		 */
	}

}
