<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5">
		<h3>User Modify Page</h3>
		<!-- 이 페이지는 로그인이 되어있어야지만 접근할 수 있는 페이지 -->
		<sec:authentication property="principal.userVO" var="user"/>
		
		<form action="/user/modify" method="post">
			<!-- CSRF 토큰 추가 -->
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
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
			    
			    <button type="submit" class="btn btn-primary btn-sm">modify</button>
			    <a href="/user/remove"><button type="button" class="btn btn-danger btn-sm">remove</button></a>
			  </div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		const modify_msg = `<c:out value="${modify_msg}" />`;
		console.log(modify_msg);
		if(modify_msg==="fail"){
			alert("회원정보 수정실패!");
		}
	</script>
<jsp:include page="../layout/footer.jsp" />
