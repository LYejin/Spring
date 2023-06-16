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
	<h3>상품목록 출력하기(EL & JSTL)</h3>
	${orderCommand.orderItem} 안에 3개방에 각각 orderItem 객체를 가지고 있다<br>
	<hr>
	<ul>
		<c:forEach var="orderItem" items="${orderCommand.orderItem}">
			<li>${orderItem.itemid}-${orderItem.number}-${orderItem.remark}</li>
		</c:forEach>
	</ul>
</body>
</html>