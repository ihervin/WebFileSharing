<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web File Sharing - Login</title>

<link href="css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>

<script>
	function loginCallSuccess(data) {
		if (data.success) {
			window.location = 'data.jsp';
		} else {
			showErrorMessages("User ID/Password tidak sesuai");
		}
	}

	function showErrorMessages(error) {
		$('#error-box').css("display", "block");
		$('#error-box').text(error);
	}
	function login() {
		$('#error-box').css("display", "none");
		$.ajax({
			type : 'POST',
			url : 'Login',
			data : {
				'userID' : $('#userID').val(),
				'password' : $('#password').val()
			},
			success : loginCallSuccess,
			dataType : 'json'
		});

		return false;
	}

	$(document).ready(function() {
<%if (request.getParameter("r") != null) {%>
	showErrorMessages('Session anda telah timeout, silakan login ulang!');
<%}%>
	});
</script>
</head>
<body>
================================================== -->
<div class="navbar navbar-fixed-top">
<div class="navbar-inner">
<div class="container"><a class="btn btn-navbar"
	data-toggle="collapse" data-target=".nav-collapse"> <span
	class="icon-bar"></span> <span class="icon-bar"></span> <span
	class="icon-bar"></span> </a> <a class="brand" href="index.jsp">Simple
File Sharing</a>
<div class="nav-collapse collapse">
<ul class="nav">
	<li class="divider-vertical"></li>
</ul>
</div>
</div>
</div>
</div>
<br />
<br />
<br />
<br />
<br />
<br />
<div class="container">
<div class="row">
<div class="span1">&nbsp;</div>
<div class="span8">
<div id="error-box" style="display: none" class="alert alert-error">
User ID/Password tidak sesuai</div>
<form class="form-horizontal">
<fieldset>
<div class="control-group"><label class="control-label"
	for="focusedInput">User ID</label>
<div class="controls"><input class="input-xlarge focused"
	id="userID" name="userID" type="text"
	value="User ID Domain Bank Indonesia"></div>
</div>
</fieldset>
<fieldset>
<div class="control-group"><label class="control-label"
	for="focusedInput">Password</label>
<div class="controls"><input class="input-xlarge focused"
	id="password" name="password" type="password"></div>
</div>
</fieldset>
<div class="form-actions"><input type="button" onClick="login()"
	class="btn btn-primary" value="Login"></input></div>
</form>
</div>
</div>
</div>
</body>
</html>