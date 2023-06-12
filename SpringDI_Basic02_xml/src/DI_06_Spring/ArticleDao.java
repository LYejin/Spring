package DI_06_Spring;

//OracleDao, MysqlDao 사용하는 공통함수(추상함수) 강제구현
public interface ArticleDao {
	void insert(Article article);
	
	//추가구현
	//CRUD
}
