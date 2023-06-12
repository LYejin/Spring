package DI_06_Spring;

public class Articleservice {
	//Articleservice는 ArticleDao 의존한다
	//Articleservice은 ArticleDao 의 주소가 필요하다
	
	//2가지 (생성자, setter) 주입
	private ArticleDao articledao; 
	
	public Articleservice(ArticleDao articledao) { // oracledao, mysqldao 다형성 // 생성자 주입
		this.articledao = articledao;
		System.out.println("Articleservice 생성자 호출");
	}
	
	//글쓰기 서비스
	public void write(Article article) { // 생성자로 생성된 articledao의 insert article 주입
		this.articledao.insert(article); 
	}
}
