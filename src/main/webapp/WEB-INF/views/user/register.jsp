<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5 loginBox">
		<div class="animatedBox registerBox">
			<form action="/user/insert" method="post">
				<h1>Register</h1>
				<!-- CSRF 토큰 추가 -->
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
				<div>
					<div class="inputBx">
						<span></span>
						<input type="text" id="e" name="email" placeholder="User Email">
					</div>
					<div class="inputBx">
						<span></span>
						<input type="password" id="p" name="pwd" placeholder="Password">
					</div>
					<div class="inputBx">
						<span></span>
						<input type="text" id="p" name="nickName" placeholder="NickName">
					</div>
					<div class="inputBx">
						<button type="submit" class="filledBg">Register</button>
					</div>
				</div>
			</form>
		</div>
	</div>
<jsp:include page="../layout/footer.jsp" />
