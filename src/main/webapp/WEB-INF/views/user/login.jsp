<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5">
		<h1>User Login Page</h1>
		<form action="/user/login" method="post">
			<!-- CSRF 토큰 추가 -->
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
			<div class="mb-3">
				<label for="e" class="form-label">Email</label>
				<input type="text" class="form-control" id="e" name="email" placeholder="email...">
			</div>
			<div class="mb-3">
				<label for="p" class="form-label">Password</label>
				<input type="password" class="form-control" id="p" name="pwd" placeholder="password...">
			</div>
			<!-- 로그인 실패시 에러메세지 출력 -->
			<c:if test="${errMsg ne null }">
				<div class="text-danger p-1">${errMsg } / ${failEmail }</div>
			</c:if>
			<button type="submit" class="btn btn-primary">Join</button>
		</form>
	</div>
<jsp:include page="../layout/footer.jsp" />
