package DI_09_Spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		
		/*
		ProtocolHandlerFactory factory = new ProtocolHandlerFactory();
		
		Map<String, ProtocolHandler> handler = new HashMap<String, ProtocolHandler>();
		handler.put("rss", new RssHandler());
		handler.put("rest", new RestHandler());
		
		factory.setHandlers(handler);
		*/
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_10_Spring/DI_10.xml");
	}

}
