<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5">
		<h1>User Join Page</h1>
		<form action="/user/insert" method="post">
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
			<div class="mb-3">
				<label for="n" class="form-label">NickName</label>
				<input type="text" class="form-control" id="n" name="nickName" placeholder="nickName...">
			</div>
			<button type="submit" class="btn btn-primary">Join</button>
		</form>
	</div>
<jsp:include page="../layout/footer.jsp" />
