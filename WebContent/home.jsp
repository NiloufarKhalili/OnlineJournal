<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>

<c:import url="header.jsp">
	<c:param name="title" value="Reader Page"></c:param>
</c:import>
<%
	String message = (String) request.getAttribute("message");
	String info = (String) request.getAttribute("info");
%>

<%-- <c:if test="${message != ''}">

 <h5 id="flash-message">${message}</h5>

</c:if> --%>
<%
	if (message != null) {
%>
<div class="alert alert-error">
	<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Error!</strong>
	<%=message%>
	<br />
</div>
<%
	}
%>
<%
	if (info != null) {
%>
<div class="alert alert-success">
	<a href="#" class="close" data-dismiss="error">&times;</a> <strong>success!</strong>
	<%=info%>
	<br />
</div>
<%
	}
%>

<div class="hero-unit " id="container">

	<h2>The Self-Resourcing Electronic Journal</h2>
	<p id="explain">Search for a particular paper by title or by
		author(s) names or by date interval or by a subject keyword search.</p>

	<div class="searcpanel">
		<ul id="tabs" class="nav nav-tabs">
			<input type="hidden" value="${pageContext.request.contextPath}" id="hidden"/>
			<li class="active" id="author"><a id="first">by author</a></li>
			<li id="title"><a id="second">by Title</a></li>
			<li id="keyword"><a id="third">by Keyword</a></li>
			<li id="date"><a id="forth">by Date</a></li>
			<li id="all"><a id="fifth" href="${pageContext.request.contextPath}/ReaderController?action=all">View All</a></li>
		</ul>
		<div class="input-prepend" id="main" style="display: '';">
			<span class="add-on"><i class="icon-search"></i></span> <input
				type="text" name="search" placeholder="Search" id="input" width="200px">
		</div>
		<div class="row" id="date-search" style="display: none;">
			<div class="span3">
			<table class="table">
				<td>From:</td><td><input type="text" id="datepicker1" name="fromDate"></td>
				<tr><td>To:</td><td><input type="text" id="datepicker2" name="toDate"></td>
			</table>
			</div>
			
		</div>
		<br /> <a id="search" href="" class="btn btn-primary btn-large">Search</a>
	</div>
</div>
<c:import url="footer.jsp"></c:import>