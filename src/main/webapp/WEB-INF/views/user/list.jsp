<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5">
		<h3>User List Page</h3>
		<div class="row justify-content-start">
			<!-- 이 페이지는 ADMIN 로그인이 되어있어야지만 접근할 수 있는 페이지 -->
			<c:forEach items="${userList }" var="user">
				<div class="col mb-3">
					<div class="card" style="width: 18rem;">
					  <img src="/resources/image/lsw.png" class="card-img-top" alt="...">
					  <div class="card-body">
					    <h5 class="card-title">
					    	<input type="hidden" class="form-control" name="email" value="${user.email }">
					    	<input type="text" class="form-control" name="nickName" value="${user.nickName }">
					    	<input type="password" class="form-control" name="pwd" placeholder="password...">
					    </h5>
					    <p class="card-text">${user.email }</p>
					    <p class="card-text">${user.regDate }</p>
					    <p class="card-text"><small>Last login ${user.lastLogin } ago</small></p>
					    
					    <!-- 권한 -->
					    <p class="card-text">
					    	<c:forEach items="${user.authList }" var="auths">
					    		<span class="badge text-bg-success">${auths.auth }</span>
					    	</c:forEach>
					    </p>
					  </div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
<jsp:include page="../layout/footer.jsp" />
