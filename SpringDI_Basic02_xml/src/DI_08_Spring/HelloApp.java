package DI_08_Spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloApp {

	public static void main(String[] args) {
		/*
		JobExecute jobexecute = new JobExecute("hong", 100);
		
		JobExecute jobexecute2 = new JobExecute("lim", 1000L);
		
		JobExecute jobexecute3 = new JobExecute("lim", "hong");
		
		ArticleDao articledao = new ArticleDao();
		jobexecute.setArticledao(articledao);
		
		jobexecute.setData(500);
		*/
		ApplicationContext context = new GenericXmlApplicationContext("classpath:DI_08_Spring/DI_08.xml");
	}

}
