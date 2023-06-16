package AOP_Basic02_Java;

import java.lang.reflect.Proxy;

public class Program {
	public static void main(String[] args) {
		
		//1. 실객체의 주소
		Calc calc = new NewCalc();
		
		//2. 속임수 (진짜 함수를 호출하는 것이 아니고 가짜를 만들어서 호출 : 보조 업무를 수행하기 위해서)
		// 가짜 (Proxy) -> Calc 반환 
		Calc cal = (Calc) Proxy.newProxyInstance(calc.getClass().getClassLoader(), //실객체의 정보 제공 (내부정보) : 메타정보 
							   calc.getClass().getInterfaces(),  //행위 정보 (인터페이스) <내가 실객체를 알아서 뭐하니?>
							   new LogPrintHandler(calc)); //보조관심 실행 (공통관심) 객체 정보
		//실제 사용자 (진짜 사용하는 것처럼 사용)
		int result = cal.ADD(555, 666);
		System.out.println("result : " + result);
	}
}
