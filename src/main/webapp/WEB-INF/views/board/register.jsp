<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5">
		<h3>Board Register Page</h3>
		<form action="/board/insert" method="post">
			<div class="mb-3">
			  <label for="t" class="form-label">Title</label>
			  <input type="text" class="form-control" id="t" name="title" placeholder="title...">
			</div>
			<div class="mb-3">
			  <label for="w" class="form-label">Writer</label>
			  <input type="text" class="form-control" id="w" name="writer" placeholder="writer...">
			</div>
			<div class="mb-3">
			  <label for="c" class="form-label">Content</label>
			  <textarea class="form-control" id="c" name="content" rows="3" placeholder="content..."></textarea>
			</div>
			<button type="submit" class="btn btn-primary">register</button>
		</form>
	</div>
<jsp:include page="../layout/footer.jsp" />
