<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<button onclick="idCheck()">아이디 있나?</button>

	<script type="text/javascript">
		function idCheck() {
			alert('되낭')

			const xhttp = new XMLHttpRequest();
			
			// 해당 함수는 통신이 끝나면 콜백
			xhttp.onload = function() {
				//document.getElementById("demo").innerHTML = this.responseText;
				alert(this.responseText)
			}
			xhttp.open("GET", "http//localhost:8181/blog/ajax", true);
			xhttp.send();
		}
	</script>
</body>
</html>