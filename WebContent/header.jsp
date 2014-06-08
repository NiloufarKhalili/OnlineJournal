<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
	<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">

</script>

<script src="${pageContext.request.contextPath}/javascripts/myscript.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/comment.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/form.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/delete.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/upload.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/reader.js"></script>
<script src="${pageContext.request.contextPath}/javascripts/editor.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheets/style.css" />
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet" />
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<title>${param.title}</title>
</head>
<body id="body" onload="">

	<div id="header">
		<nav class="navbar navbar-default navbar-top">

		<div class="navbar-inner navbar-content-center">
			<a class="brand" href="${pageContext.request.contextPath}/home.jsp">E-Journal</a>
			<ul class="nav">
				<li><a href="${pageContext.request.contextPath}/ReaderController?view=userguide"><i class="icon-book"></i>User Guide</a></li>
				<li><a href="${pageContext.request.contextPath}/views/upload-instructions.jsp"><i class="icon-upload"></i>Upload</a></li>
				<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">Author<b class="caret"></b></a>

					<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/AuthorController">My Articles</a></li>

					</ul>
				</li>
				<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">Reviewer<b class="caret"></b></a>

					<ul class="dropdown-menu">
					<li><a href="#">Reviewer Guide</a></li>
					<li><a href="${pageContext.request.contextPath}/JDBServlet?action=select_article">Select Articles </a></li>
					<li><a href="${pageContext.request.contextPath}/JDBServlet?action=await_selection">Await selection </a></li>
					<li><a href="${pageContext.request.contextPath}/JDBServlet?action=approved_article">My Reviews</a></li>
					</ul>
				</li>
				
				
				
				<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">Editor<b class="caret"></b></a>

					<ul class="dropdown-menu">
					<li><a href="${pageContext.request.contextPath}/EditorController?action=articles"><i class="icon-book"></i>Articles</a></li>
<li><a href="${pageContext.request.contextPath}/EditorController?action=users"><i class="icon-user"></i>Invite Editor</a></li>
<li><a href="${pageContext.request.contextPath}/views/journal.jsp">Journal</a></li>

</ul>
				</li>

			</ul>

			<form class="navbar-form form-inline pull-right" method="post"
				action="${pageContext.request.contextPath}/Login?action=login" name="loginForm" onsubmit="getMessege()">
				<%
					if (session.getAttribute("user") != null) {
				%>
				${user.email}
				<a class="btn btn-primary "
					href="${pageContext.request.contextPath}/Login?action=logout">Log Out</a>

				<%
					}else {
				%>

				<input type="text" placeholder="Email" name="email" /> <input
					type="password" name="password" placeholder="Password" />
				<button type="submit" class="btn btn-primary" onclick="return validateLogin()">Sign in</button>
				<a class="btn btn-primary "
					href="${pageContext.request.contextPath}/views/register.jsp">Register</a>
			  <% } %>
			</form>
		</div>
		</nav>
	</div>
	<div id="navbar-background">
<div class="container" id="main-container">