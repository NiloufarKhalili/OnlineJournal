<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>

<c:import url="/header.jsp">
	<c:param name="title" value="EditorPage"></c:param>
</c:import>
<%
	ArrayList<Article> articles = (ArrayList<Article>) session
			.getAttribute("editorArticles");
%>

<%-- <c:if test="${articles == null}">
 
  <jsp:forward page="../home.jsp"></jsp:forward>
 
 
 </c:if>  --%>

<%
	if (articles == null) {
%>
<jsp:forward page="/home.jsp"></jsp:forward>
<%
	}
%>
<c:choose>


	<c:when test="${param.id != null}">


		<%
			int id = Integer.valueOf(request.getParameter("id"));
		%>
		<%
			if (id >= articles.size()) {
		%>
		<c:redirect url="${pageContext.request.contextPath}/home.jsp"></c:redirect>


		<%
			}
		%>
		<%
			Article article = articles.get(id);

					String message = (String) request.getAttribute("message");
					String info = (String) request.getAttribute("info");
		%>
		<div class="hero-unit " id="container">
			<%
				if (info != null) {
			%>
			<div class="alert alert-info">
				<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Info!</strong>
				<%=info%>

			</div>
			<%
				}
			%>
			<div class="row">
				<div class="span8">
					<h2><%=article.getTitle()%></h2>
				</div>
				<div class="span1" style="margin-right:0px">
					
					
						<a
							href="${pageContext.request.contextPath}/EditorServiceController?source=<%= article.getPdfPath() %>"
							class="btn btn-success btn-large active">view</a>
				 </div>
				<div class="span1 offset0">

						<form action="${pageContext.request.contextPath}/EditorController"
							method="post">

							<input type="hidden" value="<%=articles.indexOf(article)%>"
								name="articleId">

							<button type="submit" class="btn btn-danger  btn-lg ">Publish
								Article</button>

						</form>
				</div>
			</div>
			<hr>
			<div class="row">
				<div class="span6">

					<h2>Abstract</h2>
					<p><%=article.getAbst()%></p>

				</div>


				<div class="span3">

					<ul style="border-width: 5px; border-style: groove">
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
			<hr />

			<h3>Main Author</h3>
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
				<tr>
					<th>Affiliation</th>
					<td><%=article.getMainUser().getAffiliation()%></td>
				</tr>

			</table>
			<hr/>		

			<div class="row">
				
				<h3>Other Authors</h3>
				
					<table class="table table-hover table-borderd">
					<tr>
							<th>Firstname</th>
							<th>Lastname</th>
							<th>Email</th>
							<th>Affiliation</th>
						</tr>
					<%
						if (article.getUsers() != null
										&& article.getUsers().size() > 0) {
					%>
			

						<%
							for (User user : article.getUsers()) {
						%>
						
						<tr>
							<td><%=user.getFirstname()%></td>
							<td><%=user.getLastname()%></td>
							<td><%=user.getEmail()%></td>
							<td><%=user.getAffiliation()%></td>

						</tr>
						<%
							}
						%>

						<%
							}
						%>
						
					</table>
				<hr/>
				<% String editorMessage = (String)request.getAttribute("editormessage");
					if(editorMessage != null){
				%>
				
				<div class="alert alert-info">
	<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Info!</strong>
	                <%= editorMessage %>
	<br />
      </div><%} %>

				<h3>Review Forms</h3>
				<%
					if (article.getForms() != null
									&& article.getForms().size() == 0) {
				%>
				<div class="alert alert-info">
					<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Info!</strong>
					Non form has been submitted for this article. <br />
				</div>
				<%
					}
				%>
				<table class="table table-striped table-hover table-borderd">
					<tr class="row">
						<th>Editor Accepted</th>
						<th>Judgment</th>
						<th>Expertise</th>
						<th>Action</th>

					</tr>
					<%
						for (ReviewForm form : article.getForms()) {
									if (form.getArticleApproved().equals("1")) {
					%>
					<%
						String progressClass;
										String progressStatus;
										if (form.getFormApproved().equals("0")) {
											progressClass = "bar-warning";
											progressStatus = "Pending";
										} else {
											progressClass = "bar-success";
											progressStatus = "Approved";
										}
					%>

					<tr class="row">
						<td class="span3">
							<div class="progress">
								<div class="progress-bar <%=progressClass%>" role="progressbar"
									style="width: 100%">
									<div class="sr-only text-center"><%=progressStatus%></div>
								</div>
							</div>
						</td>
						<td><%=form.getOverall()%></td>
						<td><%=form.getLevel()%></td>

						<td>
							<%
								if (form.getFormApproved().equals("1")) {
							%> <a
							href="${pageContext.request.contextPath}/views/editor-form.jsp?formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article) %>"
							class="btn btn-primary btn-success .disabled">view</a>

						</td>
						<%
							} else {
						%>

						<a
							href="${pageContext.request.contextPath}/views/editor-form.jsp?formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article) %>"
							class="btn btn-primary btn-warning .disabled">Approve</a>

						</td>

						<%
							}
										}
									}
						%>


					</tr>


				</table>

			</div>
			
			
			<hr />

			<h3>Reviewers awaiting for approval to peer-review this article</h3>
			<%
				if (article.getForms() != null
								&& article.getForms().size() == 0) {
			%>
			<div class="alert alert-info">
				<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Info!</strong>
				There is no peer-review request for this article <br />
			</div>
			<%
				}
			%>
			<table class="table table-striped table-hover table-borderd">
				<tr class="row">
					<th>firstname</th>
					<th>lastname</th>
					<th>email</th>
					<th>Approve</th>
					<th>Reject</th>

				</tr>
				<%
					for (ReviewForm form : article.getForms()) {
								if ((form.getArticleApproved().equals("0")&&(form.getStatus().equals("select")))) {
				%>


				<tr class="row">
					<td><%=form.getReviewer().getFirstname()%></td>
					<td><%=form.getReviewer().getLastname()%></td>
					<td><%=form.getReviewer().getEmail()%></td>
					<td><a
						href="${pageContext.request.contextPath}/EditorController?action=confirm&formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article) %>"
						class="btn btn-primary btn-success">Confirm</a></td>
					<td><a
						href="${pageContext.request.contextPath}/EditorController?action=reject&formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article) %>"
						class="btn btn-primary btn-danger">Reject</a></td>
					<%
						}

								}
					%>
				</tr>


			</table>






		</div>
	</c:when>
	<c:otherwise>
		<div class="hero-unit " id="container">
		<%		String info = (String) request.getAttribute("info");
				if (info != null) {
			%>
			<div class="alert alert-info">
				<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Info!</strong>
				<%=info%>

			</div>
			<%
				}
			%>
			<h1>Articles</h1>

			<table class="table table-striped table-hover table-borderd">
				<tr>
				<thead>
					<th>Volume</th>
					<th>Edition</th>
					<th>Title</th>
					<th>Reviewed</th>
					<th>Published</th>
					<th>Uploaded</th>
					<th>Action</th>
				</thead>
				</tr>

				<%
					for (Article article : articles) {
				%>
				<tr>
				<tbody>
					<td><%=article.getVolume()%></td>
					<td><%=article.getEdition()%></td>
					<td><%=article.getTitle()%></td>
					<td><%=article.getReview_count()%></td>
					<td><%=article.getStatus()%></td>
					<td><%=article.getCreatedAt()%></td>

					<td><a
						href="${pageContext.request.contextPath}/views/editor-articles.jsp?id=<%= articles.indexOf(article)
  
 %>"
						class="btn btn-success btn-lg active" role="button btn-sm">view</a></td>

					</td>
				</tbody>

				</tr>
				<%
					}
				%>
			</table>
		</div>

	</c:otherwise>
</c:choose>



</div>

<c:import url="/footer.jsp"></c:import>