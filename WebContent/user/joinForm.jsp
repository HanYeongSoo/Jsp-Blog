<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/blog/user?cmd=join" method="post" onsubmit="return valid()">
		<div class="d-flex justify-content-end">
			<button class="btn btn-info" type="button" onClick="usernameCheck();">아이디 중복 체크</button>
		</div>
		
		<div class="form-group">
			<input type="text"  name="username" id="username" class="form-control" placeholder="Enter Username" required >
		</div>
		
		<div class="form-group">
			<input type="password" name="password" class="form-control" placeholder="Enter Password" required >
		</div>
		
		<div class="form-group">
			<input type="email" name="email" class="form-control" placeholder="Enter email" required >
		</div>
		
		<div class="d-flex justify-content-end">
			<button class="btn btn-info" type="button" onClick="goPopup();">주소 검색</button>
		</div>
		
		<div class="form-group">
		 <input type="text" name="address" id="address" class="form-control" placeholder="Enter Address" required readonly >
		</div>
		 
		<button type="submit" class="btn btn-primary">회원 가입</button>
	</form>
</div>


<script>
	var isChecking = false;		// 아직 체크가 안된 상태
	
	function usernameCheck() {
		// DB에서 확인해서 정상이면 isChecking = true
		var username = $("#username").val()		// == document.querySelector("#username")과 같음
		
		$.ajax({
			type: "POST",
			url: "/blog/user?cmd=usernameCheck",		// localhost:8181/contextPath 까지는 생략가능
			data: username, 	// 요청할때 가져갈 body 데이터
			contentType: "text/plain; charset=utf-8",
			dataType: "text" 		// 응답 받을 데이터의 타입을 적으면 자바스크립트 오브젝트로 파싱해줌.		json or text
		}).done(function(data){		// .done == 통신이 끝나면 함수를 불러줄게 ()안에 변수명은 아무거나 하고 그 안에 값이 들어오는거 보통은 data or result
			if (data === 'ok') {		// username이 있으면
				isChecking = false;
				alert('중복되는 아이디 입니다.')
			} else {		// username없으면
				isChecking = true;
				alert('해당아이디는 사용할 수 있습니다.')
			}
		})
	}
	
	function vaild() {
		if (isChecking == false) {
			alert('아이디 중복체크는 필수입니다.')
		}
		return isChecking;
	}



// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("팝업API 호출 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function goPopup(){
	// 주소검색을 수행할 팝업 페이지를 호출합니다.
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	var pop = window.open("/blog/user/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	
	// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}


function jusoCallBack(roadFullAddr){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		var addressEl = document.querySelector("#address");
		addressEl.value = roadFullAddr;
}
</script>
</body>
</html>