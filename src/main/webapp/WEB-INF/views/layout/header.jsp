<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<sec:csrfMetaTags/>
<meta name="_csrf" content="CSRF_TOKEN_VALUE">
<meta name="_csrf_header" content="X-CSRF-TOKEN">
<title>Insert title here</title>
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">Spring</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="/board/register">Board Register</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/board/list">Board List</a>
        </li>
        
        <sec:authorize access="isAnonymous()">
        <!-- 인증이 안되어야 허용 -->
	        <li class="nav-item">
	          <a class="nav-link" href="/user/register">Join</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/user/login">Login</a>
	        </li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
        <!-- 인증이 되어야만 허용 -->
        <!-- 인증 후 (인증객체가 있는 상황) 객체 가져오기 => 현재 로그인 정보 : principal -->
        <sec:authentication property="principal" var="pri" />
	        <li class="nav-item">
		        <form action="/user/logout" method="post" id="logoutForm">
		        	<!-- CSRF 토큰 추가 -->
					<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		          <a class="nav-link" id="logoutLink" href="">Logout</a>
		        </form>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/user/modify">(${pri.username })Modify</a>
	        </li>
	        <c:if test="${pri.userVO.authList.stream().anyMatch(authVO -> authVO.auth.equals('ROLE_ADMIN')).get() }">
		        <li class="nav-item">
		          <a class="nav-link" href="/user/list">UserList(ADMIN)</a>
		        </li>
	        </c:if>
	        
        </sec:authorize>
      </ul>
    </div>
  </div>
</nav>
<script type="text/javascript">
	document.addEventListener('click',(e)=>{
		if(e.target.id == 'logoutLink'){
		    e.preventDefault(); // 기존 a 태그의 링크를 없애는 역할
		    document.getElementById('logoutForm').submit();		
		}
	})
</script>