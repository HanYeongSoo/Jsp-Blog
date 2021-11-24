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
	<div id="box"></div>

	<script type="text/javascript">
		function idCheck() {
			$.ajax("http://localhost8181/blog/ajax").done(function(data){
				alert(data);
			});
		}
	</script>
</body>
</html>