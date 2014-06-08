<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>

	
<c:import url="/header.jsp">
	<c:param name="title" value="unpublished articles"></c:param>
</c:import>
<%
		ResultSet selected = (ResultSet) request.getAttribute("selected");
        ResultSet result = (ResultSet) request.getAttribute("result");
        ResultSet downloaded = (ResultSet) request.getAttribute("downloaded");

		/* 	ResultSet selected = (ResultSet) request
					.getAttribute("selected"); */
	%>
	<form
		action="${pageContext.request.contextPath}/ReviewArticle?update=reviewer"
		method="get">

<c:if test="${selected != nul}">
	<h3>List Of Selected Articles</h3>
	<table class="table table-striped table-hover">
			<tr>
				<th>Title</th>
				<th>Abstract</th>
				<th>Remove</th>
			</tr>
			<%
				while (selected.next()) {
			%>


			<tr>
				
				<td><%= selected.getString("title") %></td>
				<td><%= selected.getString("abstract") %></td>

				<td><input type="checkbox" name="selected"
					value="<%=selected.getString("title")%>" />
			</tr>



			<%
				}
			%>
		</table>
	


</c:if>
<c:if test="${downloaded != nul}">
<h3>List Of Download Articles</h3>
<ol> <%
				while (downloaded.next()) {
			%>
	<li><%= downloaded.getString("title") %></li>
	<% } %>
</ol>
<h3>List Of Submitted Review Articles</h3>
<ol>
	
</ol>
</c:if>


<c:if test="${result != null}">
	<h1>Available Articles for Review</h1>
	<%
		//ResultSet result = (ResultSet) request.getAttribute("result");
			
	%>
<%-- 
	<form
		action="${pageContext.request.contextPath}/ReviewArticle?update=reviewer"
		method="get"> --%>
		<table class="table table-striped table-hover">
			<tr>
				<th>Title</th>
				<th>Abstract</th>
				<th>Select</th>
			</tr>
			<%
				while (result.next()) {
			%>


			<tr>
				<td><%=result.getString("title")%></td>
				<td><%=result.getString("abstract")%></td>

				<td><input type="checkbox" name="articles"
					value="<%=result.getString("title")%>" />
			</tr>



			<%
				}
			%>
		</table>
		<button>Update your</button><br/><br/>
	</form>


</c:if>



<c:import url="/footer.jsp"></c:import>
