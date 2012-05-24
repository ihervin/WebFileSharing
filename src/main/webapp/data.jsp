<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web File Sharing - List File</title>
<link href="css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/data.js"></script>
<script>
	
<%if (session.getAttribute("logged_user") == null) {%>
		
	window.location = "index.jsp";
	<%}%>
	$(document).ready(function() {
		initDirList();
	});
</script>
</head>
<body>
<div class="navbar navbar-fixed-top">
<div class="navbar-inner">
<div class="container"><a class="btn btn-navbar"
	data-toggle="collapse" data-target=".nav-collapse"> <span
	class="icon-bar"></span> <span class="icon-bar"></span> <span
	class="icon-bar"></span> </a> <a class="brand" href="./index.html">Web File
Sharing</a>
<div class="nav-collapse collapse">
<ul class="nav pull-right">
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
<div class="span3">
<div class="well" style="padding: 8px 0;">
<ul id="nav-left" class="nav nav-list">
	<li class="divider"></li>
	<li><a href="#" onclick="logoutUser()"><i class="icon-lock"></i> Logout</a></li>
</ul>
</div>
</div>
<div class="span9">

<table class="table table-striped">
	<thead>
		<tr>
			<th class="span1">#</th>
			<th class="span5">Nama File</th>
			<th class="span2">Timestamp</th>
		</tr>
	</thead>
	<tbody id="t-data-body">
	</tbody>

</table>
</div>
</div>
</div>
<form id="frm_download" method="post" action="DownloadFile"><input
	type="hidden" name="dir_name" id="dir_name"></input> <input
	type="hidden" name="file_name" id="file_name"></input></form>
</body>
</html>