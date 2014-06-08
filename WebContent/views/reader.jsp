<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>

<c:import url="/header.jsp">
	<c:param name="title" value="Reader"></c:param>
</c:import>


<div class="hero-unit " id="container">

	<%
		ArrayList<Article> articles = (ArrayList<Article>) session
				.getAttribute("readerArticles");
	%>

	<%
		if (articles == null) {
	%>
	<jsp:forward page="../home.jsp"></jsp:forward>
	<%
		} else if ((request.getParameter("id") == null)) {
	%>

	<h2>Articles</h2>
	<hr/>
	<table class="table table-striped table-hover table-borderd">
		<thead>
			<tr>
				<th>Volume</th>
				<th>Edition</th>
				<th>Title</th>
				<th>Uploaded</th>
				<th>Action</th>

			</tr>
		</thead>
		<tbody>
			<%
				for (Article article : articles) {
			%>

			<tr>

				<td><%=article.getVolume()%></td>

				<td><%=article.getEdition()%></td>
				<%
					request.setAttribute("articles", articles);
				%>

				<td><a
					href="${pageContext.request.contextPath}/views/reader.jsp?id=<%= articles.indexOf(article) %>"><%=article.getTitle()%></a></td>

				<td><%= String.valueOf(article.getCreatedAt()) %></td>
				<td><a
					href="${pageContext.request.contextPath}/views/reader.jsp?id=<%= articles.indexOf(article) %>"
					class="btn btn-primary btn-lg active">view</a></td>
			</tr>

			<%
				}
			%>

		</tbody>

	</table>


	<%
		} else {
	%>

	<%
		Article article = articles.get(Integer.valueOf(request
					.getParameter("id")));
	%>
	<div>

		<h1><%=article.getTitle()%></h1>
		<hr>
		<div class="row">
			<div class="span6">

				<h2>Abstract</h2>
				<p><%=article.getAbst()%></p><br/>
				<a
					href="${pageContext.request.contextPath}/ReaderController?id=<%= articles.indexOf(article) %>"
					class="btn btn-success btn-large active">view</a><br/>
			</div>
			
			
			<div class="span3">
			
				<ul style="border-width:5px;	
border-style:groove">
					<dt>Keywords</dt>
					<%
						for (Keyword keyword : article.getKeywords()) {
					%>
					<dd><%=keyword.getWord()%></dd>


					<%
						}
					%>
				</ul>


			</div>
		</div>
		<hr/>
		
		<h3>Main User</h3>
		<table class="table table-hover table-borderd">
			<tr>
				<th>Firstname</th>
				<td><%=article.getMainUser().getFirstname()%></td>
			</tr>
			<tr>
				<th>Lastname</th>
				<td><%=article.getMainUser().getLastname()%></td>

			</tr>
			<tr>
				<th>Email</th>
				<td><%=article.getMainUser().getEmail()%></td>
			</tr>

		</table>

		<div class="row">
			<div class="span10">
				<h4>Other users</h4>
				<table class="table table-hover table-borderd">
				
			<tr>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Email</th>
				<th>Affiliation</th>
			</tr>
				<% if(article.getUsers() != null && article.getUsers().size() > 0) {%>
				<% for(User user:article.getUsers()) {%>
			<tr>
				<td><%= user.getFirstname()%></td>
				<td><%= user.getLastname()%></td>
				<td><%= user.getEmail()%></td>
				<td><%= user.getAffiliation()%></td>

			</tr>
			<%} %>
			
<%} %>
		</table>
			
			</div>
		</div>
	</div>
		<hr/>
		<% String message = (String)request.getAttribute("message"); %>
		<% if(message != null) { %>
		<div class="alert alert-success">
	<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Success!</strong>
	<%=message%>
	<br />
</div>

		<%} %>
	

	<form class="form" name="reader" method="post"
				action="${pageContext.request.contextPath}/ReaderController" onsubmit="return validateComment()">
 				<h3>Send a comment to the editor</h3>
 				
				<input type="hidden" value="<%=articles.indexOf(article)%>" name="articleId">
						
				
							 <div id="emaildiv">
							      <input  type="text" placeholder="your email" id="email" name="email">
							 </div>
							 <div id="titlediv">
							      <input  type="text" placeholder="your comment title" id="title" name="title">
							 </div>
							 <div id="commentdiv">
							 				<textarea rows="10" cols="10" id="comment" name="comment" placeholder="your comment"></textarea>
							 
							 </div>
      
			    <input class="btn btn-primary" type="submit"
							value="Post comment" id="readerComment">
      
				</form>

	<%
		}
	%>


</div>

<c:import url="/footer.jsp"></c:import>
