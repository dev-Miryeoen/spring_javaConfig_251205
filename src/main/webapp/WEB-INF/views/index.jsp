<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="layout/header.jsp" />
	<div class="container-sm p-5 mainContainer">
		
		<div>
			<a href="/board/register">글쓰기</a>
		</div>
		<div>
			<a href="/board/list">글목록</a>
		</div>
		
		<p class="mainTime">${serverTime}.</p>
	</div>
	<script type="text/javascript">
		const modify_msg = `<c:out value="${modify_msg}" />`;
		console.log(modify_msg);
		if(modify_msg==="ok"){
			alert("회원정보가 수정되었습니다. 다시 로그인 해 주세요.");
		}
		
		const remove_msg = `<c:out value="${remove_msg}" />`;
		console.log(remove_msg);
		if(remove_msg==="ok"){
			alert("화원탈퇴가 완료되었습니다.");
		}
		if(remove_msg==="fail"){
			alert("화원탈퇴가 실패하였습니다. 다시 시도해주세요.");			
		}
	</script>
<jsp:include page="layout/footer.jsp" />
