package DI3_interface;

import java.util.Scanner;

//점수를 출력하는 클래스
public class NewRecordView implements RecordView {
	//NewRecordView는 점수를 받기 위해서 NewRecord가 필요합니다
	//상속 : 포함 ** 
	
	private NewRecord record; //member field가 NewRecord 객체의 주소를 가지겠다
	
	//NewRecord 객체의 주소를 필요에 따라서 주입 받고 있어요
	//NewRecordView 만들어진다고 해서 무조건 record가 NewRecord 주소를 받을 필요는 없어요
	
	//여기를 수정할게요 .......
	//답은 함수
	//public void setRecord(NewRecord record) {
	public void setRecord(Record record) { // interface 사용해서 다형성(부모타입)
		this.record = (NewRecord) record;
	}
	/*
	 나는 니가 필요해
	 나는 너의 객체 [주소]가 필요해
	 
	 1. 생성자를 통해서 필요한 객체를 생성 또는 주입 받을 수 있다 > DI > 복합(직접 생성), 집합(주입 받으면)
	 2. 함수(setter)를 통해서 필요한 객체를 주입 > DI > 집합 > 필요에 따라서
	 */
	
	public void print() {
		System.out.println(record.total() + " / " + record.avg());
	}

	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);
		System.out.println("kor");
		record.setKor(Integer.parseInt(sc.nextLine()));

		System.out.println("eng");
		record.setEng(Integer.parseInt(sc.nextLine()));
		
		System.out.println("math");
		record.setMath(Integer.parseInt(sc.nextLine()));
		
	}
}

