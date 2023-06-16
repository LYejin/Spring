<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원조회</title>
</head>
<body>
	Session Value : <c:out value="${sessionScope.USERID}" />
	<form action="${pageContext.servletContext.contextPath}/login.do" method="post">
		<table border="1">
			<tr>
				<td>아이디:</td>
				<td>
					<input type="text" name="id" size="20">
				</td>
			</tr>
			<tr>
				<td>비밀번호:</td>
				<td>
					<input type="password" name="pwd" size="20">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="로그인">
					<input type="reset" value="취소">
				</td>
			</tr>
		</table>		
	</form>
</body>
</html>