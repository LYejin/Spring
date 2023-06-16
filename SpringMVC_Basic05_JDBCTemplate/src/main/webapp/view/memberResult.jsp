<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 결과</title>
</head>
<body>
EL 출력<hr>
아이디 :${membervo.id}<br>
이  름 :${membervo.name}<br>
이메일 :${membervo.email}<br>
나이   :${membervo.age}<br>

</body>
</html>