<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5 detailBox">
		<h3>글 상세</h3>
		
		<!-- 값을 저장하는 변수처럼 사용 -->
		<c:set value="${boardFileDTO.board }" var="board" />
		<div class="d-flex detail-inputBx-container">
			<div class="mb-3 inputBx">
			  <label for="b">No.</label>
			  <input type="text" class="form-control" id="b" name="bno" value="${board.bno }" readonly>
			  <span></span>
			</div>
			<div class="mb-3 inputBx">
			  <label for="t">Title.</label>
			  <input type="text" class="form-control" id="t" name="title" value="${board.title }" readonly>
			  <span></span>
			</div>
			<div class="mb-3 inputBx">
			  <label for="w">Writer</label>
			  <input type="text" class="form-control" id="w" name="writer" value="${board.writer }" readonly>
			  <span></span>
			</div>
			<div class="mb-3 inputBx">
			  <label for="r">Date.</label>
			  <input type="text" class="form-control" id="r" name="regdate" value="${board.regdate }" readonly>
			  <span></span>
			</div>
			<div class="mb-3 inputBx">
			  <label for="rc">Count.</label>
			  <input type="text" class="form-control" id="rc" name="readCount" value="${board.readCount }" readonly>
			  <span></span>
			</div>
		</div>
		<div class="mb-3 inputBx w-100">
		  <textarea class="form-control" id="c" name="content" rows="3" readonly>${board.content }</textarea>
		  <span></span>
		</div>
		
		<!-- file print -->
		<div class="mb-3">
		  <ul class="list-group list-group-flush">
		  	<!-- 파일의 갯수만큼 li가 반복 type=1 이면 그림을 표시, 아니면 파일모양(다운로드가능)으로 표시 -->
		  	<c:forEach items="${boardFileDTO.flist }" var="fvo">
			  	<li class="list-group-item">
			  		<c:choose>
			  			<c:when test="${fvo.fileType > 0 }">
			  				<!-- 그림파일 -->
			  				<div>
			  					<img alt="" src="/upload/${fvo.saveDir }/${fvo.uuid}_th_${fvo.fileName}">
			  				</div>
			  			</c:when>
			  			<c:otherwise>
			  				<!-- 일반파일 : 다운로드 가능 -->
			  				<a href="/upload/${fvo.saveDir }/${fvo.uuid}_${fvo.fileName}" download="${fvo.fileName}">
			  					<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
								  <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
								  <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
								</svg>
			  				</a>
			  			</c:otherwise>
			  		</c:choose>
			  		<div class="mb-3">
			  			<div class="fw-bold">${fvo.fileName }</div>
			  		</div>
			  		<span class="badge text-bg-primary">${fvo.regDate } / ${fvo.fileSize }Bytes</span>
			  	</li>
		  	</c:forEach>
		  </ul>
		</div>
		<div class="d-flex justify-content-end">
			<a href="/board/list"><button type="button" class="btn btn-primary">list</button></a>
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal.userVO.nickName" var="authNick" />
				<sec:authentication property="principal.userVO.email" var="authEmail" />
				<c:if test="${board.email eq authEmail }">
					<a href="/board/modify?bno=${board.bno }"><button type="button" class="btn btn-warning">modify</button></a>
					<a href="/board/delete?bno=${board.bno }"><button type="button" class="btn btn-danger">delete</button></a>
				</c:if>
			</sec:authorize>
		</div>
		
		<!-- comment -->
		<!-- post -->
		<sec:authorize access="isAuthenticated()">
			<div class="mb-3 my-3 align-items-center row">
			  <span class="col-2" id="cmtWriter">${authNick }</span>
			  <div class="inputBx col-8">
				  <span></span>
				  <input type="text" class="form-control" id="cmtText" placeholder="Add Comment..." aria-label="Username" aria-describedby="basic-addon1">
			  </div>
			  <div class="inputBx col-2">
				  <button type="button" id="cmtAddBtn" class="filledBg">post</button>
			  </div>
			</div>
		</sec:authorize>
		
		<!-- print -->
		<ul class="list-group list-group-flush" id="cmtListArea"></ul>
		
		<!-- 더보기 버튼 : 한 페이지에 5개씩 댓글 표시 더 있으면 더보기 버튼 활성화 -->
		<div class="mb-3">
			<button type="button" id="moreBtn" data-page="1" style="visibility: hidden;" class="btn btn-outline-success">More +</button>
		</div>
		
		<!-- 모달 -->
		<div class="modal fade" id="cmtModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="cmtModalLabel">Modal title</h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		        <input type="text" id="cmtTextMod" class="form-control" />
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-primary" id="cmtModBtn">Modify</button>
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
<script type="text/javascript">
	const bnoValue = `<c:out value="${board.bno}" />`;
	const loginEmail = `<c:out value="${authEmail}" />`;
</script>
<script type="text/javascript" src="/resources/js/boardDetailComment.js"></script>
<script type="text/javascript">
	spreadCommentList(bnoValue);
</script>
<jsp:include page="../layout/footer.jsp" />
