<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.Error"%>
<%@ page import="beans.*"%>

<c:import url="/header.jsp">
	<c:param name="title" value="Users"></c:param>
</c:import>

<c:choose>
	<c:when test="${param.articleId eq null}">
		<c:redirect url="../home.jsp"></c:redirect>
	</c:when>
	<c:when test="${param.formId eq null}">
		<c:redirect url="../home.jsp"></c:redirect>
	</c:when>

	<c:otherwise>

		<%
			int number = 0;
					ArrayList<Article> articles = (ArrayList<Article>) session.getAttribute("editorArticles");
					String articleId = request.getParameter("articleId");
					String formId = request.getParameter("formId");
					Article article = 
							articles.get(Integer
							.valueOf(articleId));
					ReviewForm form = article.getForms().get(
							Integer.valueOf(formId));
		%>
		<% if((form.getReasons().size() == 0) || (form.getReasons().get(0).getComments().size() == 0))

		{ %>
			<% request.setAttribute("editormessage", "This form has not been completed yet"); %>
		<jsp:forward page="/views/editor-articles.jsp">
		
			<jsp:param value="<%=articleId %>" name="id"/>
		
		
		</jsp:forward>
		<% } %>
		<!--  (if the author have already replied the form before it gets the info box message) -->
		
		<div class="hero-unit ">
		<h3>Form for <%= article.getTitle() %></h3>
			<div class="row">
			<div class="span4" style="background-color:#6699FF
			">
			<h3>Summary</h3>
			<p><%= form.getSummary()%> </p>
			
			</div>
			<div class="span4 offset1" style="background-color:#6699FF
			">
			<h3>Secret Message</h3>
			<p><%= form.getSecrete() %></p>
			
			</div>
			</div>
			<hr/>
			<h4>Criticism</h4>
			<form name="author" method="get" id="reply"
				action="<%=response.encodeUrl(request.getContextPath())
							+ "/FormController"%>">
				<input type="hidden" name="articleId" value="<%=articleId%>">
				<input type="hidden" name="formId" value="<%=formId%>">


				<%
					for (Reason reason : form.getReasons()) {
				%>
				<div class="container">

					<div class="row">
						<div class="span4 offset5">
							<h3>
								Title:
								<%=reason.getTitle()%>
							</h3>
						</div>
					</div>
					<div class="row">

						<%
							ArrayList<Comment> comments = reason.getComments();
										for (int i = 0; i < comments.size(); i++) {
						%>



						<%
							if (i % 2 == 0) {
						%>

						<div class="span3">
							<p>
								date:
								<%=comments.get(i).getCreatedAt()%>
							</p>
							<textarea style="background-color: #BFE9FF
;" rows="8" cols="8">Reviewer said:<%=comments.get(i).getContent()%></textarea>

						</div>
						<%
							} else {
						%>
						<div class="span3">
							<p>
								date:

								<%=comments.get(i).getCreatedAt()%>
							</p>
							<textarea style="background-color: #C0C0C0;" rows="8" cols="8">Author replied:<%=comments.get(i).getContent()%></textarea>


						</div>


						<%
							}
										}
						%>
					</div>
					</div>
					<%
						}
					%>
					<div class="row">
					<div class="span6">
					<h4>Small Errors</h4>
					<% int i= 1; %>
					
					<table class="table table-striped table-hover ">
					<tr>
						<th>No</th>
						<th>Error</th>
						</tr>
					<% for(Error e:form.getErrors()){ %>
					<tr>
						<td><%=i++ %></td>
						<td><%=e.getError() %></td>
						</tr>

					
					<%} %>
					</div>
					</table> 
					</div>
					</div>
					

					<%-- <%
						if (form.getFormApproved().equals("0")) {
					%> --%>

					<a
							href="${pageContext.request.contextPath}/EditorController?approve=true&formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article)%>"
							class="btn btn-primary btn-success .disabled">Approve Form</a> 
					<a
							href="${pageContext.request.contextPath}/EditorController?approve=false&formId=<%= article.getForms().indexOf(form)
  
 %>&articleId=<%= articles.indexOf(article)%>"
							class="btn btn-primary btn-danger .disabled">Reject Form</a> 

					<%-- <%
						}
					%> --%>
				
			</form>
		</div>



	</c:otherwise>
</c:choose>

<c:import url="/footer.jsp"></c:import>