<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5 loginBox">
		<div class="animatedBox">
			<form action="/user/login" method="post">
				<h1>Login</h1>
				<!-- CSRF 토큰 추가 -->
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
				<div class="inputBx">
					<span></span>
					<input type="text" id="e" name="email" placeholder="User Email">
				</div>
				<div class="inputBx">
					<span></span>
					<input type="password" id="p" name="pwd" placeholder="Password">
				</div>
				<!-- 로그인 실패시 에러메세지 출력 -->
				<c:if test="${errMsg ne null }">
					<div class="text-danger p-1">${errMsg } / ${failEmail }</div>
				</c:if>
				<div class="inputBx">
					<button type="submit" class="filledBg">Login</button>
				</div>
				<div class="group">
					<a href="/user/register">register</a>
				</div>
			</form>
		</div>
	</div>
<jsp:include page="../layout/footer.jsp" />
