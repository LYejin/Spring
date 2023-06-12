package DI_02;

public class MessageBean_eng implements MessageBean{

	@Override
	public void sayHello(String name) {
		System.out.println("Hello " + name + " !");
	}

}
