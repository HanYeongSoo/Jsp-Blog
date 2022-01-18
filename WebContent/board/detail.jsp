<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">

	<c:if test="${sessionScope.loginUser.id == dto.userId }">
		<button onclick="deleteById(${dto.id})" class="btn btn-danger">삭제</button>
	</c:if>
	
	<script type="text/javascript">
		function deleteById(boardId) {
			// 요청과 응답을 json으로 통일
			var data = {
					boardId: boardId	// 앞에 있는 boardId는 변수 명 , 뒤에있는 boardId는 함수에 넘어온 매게변수
			}
			
			$.ajax({
				type: "POST",
				url: "/blog/board?cmd=delete",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(result){
				if (result.status == "ok") {
					location.href="index.jsp";
				} else {
					alert('삭제에 실패하였습니다.');
				}
			});
		}
	</script>
	<br />
	<br />
	<h6 class="m-2">
		작성자 : <i>${dto.username }</i> 조회수 : <i>${dto.readCount }</i>
	</h6>
	<br />
	<h3 class="m-2">
		<b>${dto.title }</b>
	</h3>
	<hr />
	<div class="form-group">
		<div class="m-2">${dto.content }</div>
	</div>
	<hr />
	
	<!-- 댓글 박스 -->
	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
					<div class="panel-heading m-2">
						<b>Comment</b>
					</div>
					<div class="panel-body">
						<textarea id="content" id="reply__write__form"
							class="form-control" placeholder="write a comment..." rows="2"></textarea>
						<br>

						<button
							onClick="#"
							class="btn btn-primary pull-right">댓글쓰기</button>

						<div class="clearfix"></div>
						<hr />

						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">
								<!-- 댓글 아이템 -->
								<li id="reply-1" class="media">
									<img src="/blog/images/userProfile.png" alt="" class="img-circle" style="width: 100px; height: 100px; border-radius: 50%" >
									<div class="media-body">
										<strong class="text-primary">한영수</strong>
										<p>댓글 입니다.</p>
									</div>
									<div class="m-2">
											<i onclick="#" class="material-icons">delete</i>
									</div>
								</li>

						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->
</div>

</body>
</html>

