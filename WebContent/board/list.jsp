<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../layout/header.jsp" %>	<!-- 모든 파일에 이 header가 있어야 함! -->

	<h1>세션 테스트</h1>
	로그인 한 유저네임 : ${sessionScope.loginUser.username }
</body>
</html>


