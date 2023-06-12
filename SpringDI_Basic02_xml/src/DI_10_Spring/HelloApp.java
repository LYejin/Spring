package DI_10_Spring;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		
		/*
		BookClient client = new BookClient();
		
		Properties prop = new Properties();
		prop.setProperty("server", "192.168.0.46");
		prop.setProperty("connectiontimeout", "20");
		client.setConfig(prop);
		
		client.connect();
		*/
		
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_10_Spring/DI_10.xml");
		BookClient client = context.getBean("bookclient", BookClient.class);
		client.connect();
	}

}
