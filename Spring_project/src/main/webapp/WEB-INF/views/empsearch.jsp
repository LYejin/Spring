<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="emplist.htm">목록으로 돌아가기</a>
	<div>
		<table>
			<thead>
				<tr>
					<td>EMPNO</td>
					<td>ENAME</td>
					<td>JOB</td>
					<td>MGR</td>
					<td>HIREDATE</td>
					<td>SAL</td>
					<td>COMM</td>
					<td>DEPTNO</td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="empno">${emp.empno}</td>
					<td class="ename">${emp.ename}</td>
					<td class="job">${emp.job}</td>
					<td class="mgr">${emp.mgr}</td>
					<td class="hiredate">${emp.hiredate}</td>
					<td class="sal">${emp.sal}</td>
					<td class="comm">${emp.comm}</td>
					<td class="deptno">${emp.deptno}</td>
					<td class="filename">${emp.filename}</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>