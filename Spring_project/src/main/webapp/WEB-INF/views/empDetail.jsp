<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form action="empDetail.htm" method="post">
		<a href="emplist.htm">목록으로 돌아가기</a>
		<input type="submit" value="수정">
		<a class="btn-del button" href="empDel.htm?empno=${emp.empno}">삭제</a>
		<div>
			<table>
					<tr>
						<td>EMPNO</td>
						<td class="empno"><input type="number" name="empno" value="${emp.empno}"></td>
					</tr>
					<tr>
						<td>ENAME</td>
						<td class="ename"><input type="text" name="ename" value="${emp.ename}"></td>
					</tr>
					<tr>
						<td>JOB</td>
						<td class="job"><input type="text" name="job" value="${emp.job}"></td>
					</tr>
					<tr>
						<td>MGR</td>
						<td class="mgr"><input type="number" name="mgr" value="${emp.mgr}"></td>
					</tr>
					<tr>
						<td>SAL</td>
						<td class="sal"><input type="number" name="sal" value="${emp.sal}"></td>
					</tr>
					<tr>
						<td>COMM</td>
						<td class="comm"><input type="number" name="comm" value="${emp.comm}"></td>
					</tr>
					<tr>
						<td>DEPTNO</td>
						<td class="deptno"><input type="number" name="deptno" value="${emp.deptno}"></td>
					</tr>
			</table>
		</div>
	</form>
	
</body>
</html>