<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css?rn=ygy" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<c:set var="countList" value="${fn:length(list) }"/>
					<table class="tbl-ex">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>&nbsp;</th>
						</tr>
						<c:forEach var="vo" items="${list }" varStatus="status">
							<tr>
								<td>${countList - status.index }</td>
								<c:choose>
									<c:when test="${vo.depth > 0 }">
										<td class="left" style="padding-left:${20*vo.depth}20px">
											<img src="${pageContext.request.contextPath }/assets/images/reply.png">
											<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
										</td>
									</c:when>
									<c:otherwise>
										<td class="left">
											<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
										</td>
									</c:otherwise>
								</c:choose>
								<td>${vo.userName }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
								<c:if test="${vo.userNo == authUser.no }">
									<td><a href="${pageContext.request.contextPath }/board/delete/${vo.no }" class="del">삭제</a></td>
								</c:if>
							</tr>
						</c:forEach>		
					</table>
				
			<div class="pager">
				<ul>
					<li><a href="">◀</a></li>
					<li><a href="">1</a></li>
					<li><a href="">2</a></li>
					<li class="selected">3</li>
					<li><a href="">4</a></li>
					<li><a href="">5</a></li>
					<li><a href="">▶</a></li>
				</ul>
			</div>
			<c:choose>
				<c:when test="${empty authUser }">
				
				</c:when>
				<c:otherwise>
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
					</div>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>