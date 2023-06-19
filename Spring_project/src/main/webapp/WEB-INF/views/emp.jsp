<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="get" action="empsearch.htm">
		<input type="text" name="empno" />
		<input type="submit" value="검색">
	</form>
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
				</tr>
			</thead>
			<tbody>
				<c:forEach  var="n" items="${list}" >
				<tr>
					<td class="empno"><a href="empDetail.htm?empno=${n.empno}">${n.empno}</a></td>
					<td class="ename">${n.ename}</td>
					<td class="job">${n.job}</td>
					<td class="mgr">${n.mgr}</td>
					<td class="hiredate">${n.hiredate}</td>
					<td class="sal">${n.sal}</td>
					<td class="comm">${n.comm}</td>
					<td class="deptno">${n.deptno}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<a href="empinsert.htm">사원등록</a>
</body>
</html>