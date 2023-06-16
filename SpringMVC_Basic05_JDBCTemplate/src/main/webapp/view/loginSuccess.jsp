<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
</head>
<body>
  로그인 ID : <c:out value="${sessionScope.USERID}" /> 님 로그인 성공
 <hr>
 로그아웃 : <a href="logout.do">로그아웃</a>
</body>
</html>