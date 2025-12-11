<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5 mb-5">
		<h3>Board Modify Page</h3>
		
		<!-- 값을 저장하는 변수처럼 사용 -->
		<c:set value="${boardFileDTO.board }" var="board" />
		
		<form action="/board/update" method="post" enctype="multipart/form-data">
			<div class="mb-3">
			  <label for="b" class="form-label">Bno</label>
			  <input type="text" class="form-control" id="b" name="bno" value="${board.bno }" readonly>
			</div>
			<div class="mb-3">
			  <label for="t" class="form-label">Title</label>
			  <input type="text" class="form-control" id="t" name="title" value="${board.title }" placeholder="title...">
			</div>
			<div class="mb-3">
			  <label for="w" class="form-label">Writer</label>
			  <input type="text" class="form-control" id="w" name="writer" value="${board.writer }" readonly>
			</div>
			<div class="mb-3">
			  <label for="r" class="form-label">regDate</label>
			  <input type="text" class="form-control" id="r" name="regdate" value="${board.regdate }" readonly>
			</div>
			<div class="mb-3">
			  <label for="rc" class="form-label">readCount</label>
			  <input type="text" class="form-control" id="rc" name="readCount" value="${board.readCount }" readonly>
			</div>
			<div class="mb-3">
			  <label for="c" class="form-label">Content</label>
			  <textarea class="form-control" id="c" name="content" rows="3" placeholder="content...">${board.content }</textarea>
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
				  		<button type="button" data-uuid="${fvo.uuid }" class="btn btn-danger btn-sm file-x">x</button>
				  	</li>
			  	</c:forEach>
			  </ul>
			</div>
			
			<!-- 첨부파일 업로드 라인 -->
			<div class="mb-3">
			  <label for="file" class="form-label"></label>
			  <!-- multiple : 한번에 여러개의 파일 업로드 가능 -->
			  <input type="file" class="form-control" id="file" name="files" multiple style="display:none;">
			  <button type="button" class="btn btn-outline-dark" id="trigger">file</button>
			</div>
			
			<!-- 파일 목록 라인 -->
			<div class="mb-3" id="fileZone"></div>
			
			<button type="submit" class="btn btn-success" id="regBtn">update</button>
			<a href="/board/list"><button type="button" class="btn btn-primary">list</button></a>
		</form>
	</div>
<script type="text/javascript" src="/resources/js/boardRegisterFile.js"></script>
<script type="text/javascript" src="/resources/js/boardModify.js"></script>
<jsp:include page="../layout/footer.jsp" />
