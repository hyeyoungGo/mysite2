<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath }/guestbook/insert" method="post">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<c:set var="countList" value="${fn:length(list) }"/>
				<c:forEach var="vo" items="${list }" varStatus="status">
					<br/>
					<table width=510 border=1>
						<tr>
							<td>[${countList - status.index }]</td>
							<td>${vo.name }</td>
							<td>${vo.regDate }</td>
							<td><a href="${pageContext.request.contextPath }/guestbook/deleteform/${no }">삭제</a></td>
						</tr>
						<tr>
							<td colspan=3>${vo.content }</td>
							<td><a href="${pageContext.request.contextPath }/guestbook/modifyform/${no }">수정</a></td>
						</tr>
					</table>
				</c:forEach>
			</div>
		</div>
		<c:import url = "/WEB-INF/views/include/navigation.jsp" />
		<c:import url = "/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>