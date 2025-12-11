<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
	<div class="container-sm p-5">
		<!-- search -->
		<div class="container-fluid m-2">
			<form class="d-flex" role="search" action="/board/list" method="get">
				<select class="form-select" name="type" aria-label="Default select example">
				  <option ${ph.pgvo.type eq null? 'selected':'' }>Choose...</option>
				  <option value="t" ${ph.pgvo.type eq 't'? 'selected':'' }>title</option>
				  <option value="w" ${ph.pgvo.type eq 'w'? 'selected':'' }>writer</option>
				  <option value="c" ${ph.pgvo.type eq 'c'? 'selected':'' }>content</option>
				  <option value="tc" ${ph.pgvo.type eq 'tc'? 'selected':'' }>title + content</option>
				  <option value="wc" ${ph.pgvo.type eq 'wc'? 'selected':'' }>writer + content</option>
				  <option value="tw" ${ph.pgvo.type eq 'tw'? 'selected':'' }>title + writer</option>
				  <option value="twc" ${ph.pgvo.type eq 'twc'? 'selected':'' }>all</option>
				</select>
		        <input class="form-control me-2" type="search" name="keyword" value="${ph.pgvo.keyword }" placeholder="Search" aria-label="Search"/>
		        <input type="hidden" name="pageNo" value="1">
		        <input type="hidden" name="qty" value="${ph.pgvo.qty }">
		        <button type="submit" class="btn btn-success position-relative">
				  Search
				  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">
				    ${ph.totalCount }
				    <span class="visually-hidden">unread messages</span>
				  </span>
				</button>
		    </form>
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th scope="row">#</th>
					<th scope="row">title</th>
					<th scope="row">writer</th>
					<th scope="row">reg_date</th>
					<th scope="row">read_count</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="b">
					<tr>
						<td>${b.bno }</td>
						<td>
							<a href="/board/detail?bno=${b.bno }" style="text-decoration: none;">
								${b.title }
								<c:if test="${b.fileQty ne 0 }">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-paperclip" viewBox="0 0 16 16">
									  <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0z"/>
									</svg>
									<span class="badge rounded-fill text-primary">[${b.fileQty}]</span>
								</c:if>
								<c:if test="${b.cmtQty > 0 }">
									<span class="badge rounded-fill text-warning">[${b.cmtQty}]</span>
								</c:if>
							</a>
						</td>
						<td>${b.writer }</td>
						<td>${b.regdate }</td>
						<td>${b.readCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- Paging -->
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item ${ph.prev eq false ? 'disabled' : '' }">
		      <a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
		    <c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
			    <li class="page-item"><a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a></li>
		    </c:forEach>
		    <li class="page-item ${ph.next eq false ? 'disabled' : '' }">
		      <a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty }&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
		  </ul>
		</nav>
		
		<button type="button" class="btn btn-primary">index</button>
	</div>
<jsp:include page="../layout/footer.jsp" />
