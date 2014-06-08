<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>
<%@ page import="beans.Error"%>


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
					String articleId = request.getParameter("articleId");
					String formId = request.getParameter("formId");
					Article article = ((ArrayList<Article>) session
							.getAttribute("articles")).get(Integer
							.valueOf(articleId));
					ReviewForm form = article.getForms().get(
							Integer.valueOf(formId));
		%>
		<% if((form.getReasons().size() == 0) || (form.getReasons().get(0).getComments().size() == 0))
		{ %>
		<jsp:forward page="/views/author-articles.jsp">
		
			<jsp:param value="<%=articleId %>" name="id"/>
		
		
		</jsp:forward>
		<% } %>
		<!--  (if the author have already replied the form before it gets the info box message) -->
		<% if((form.getReasons().get(0).getComments().size()%2) == 0){ %>
		<div class="alert alert-info">
			<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Info!</strong>
			You have already replied to this reviewer<br> Pleas reply to
			other reviewer's form and submit a revised pdf.
		</div>
		<% } %>
		<div class="hero-unit ">
			<h3>Summary</h3>
			<p><%=form.getSummary()%></p>
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
							<h4>
								Title:
								<%=reason.getTitle()%>
							</h4>
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
							<textarea style="background-color: #BFE9FF;" rows="8" cols="8">Reviewer said:<%=comments.get(i).getContent()%></textarea>

						</div>
						<%
							} else {
						%>
						<div class="span3">
							<p>
								date:

								<%=comments.get(i).getCreatedAt()%>
							</p>
							<textarea style="background-color: #C0C0C0;" rows="8" cols="8">You replied:<%=comments.get(i).getContent()%></textarea>


						</div>


						<%
							}
										}
										if (comments.size() % 2 != 0) {
						%>
						<div class="span3">
							<br />
							<textarea rows="8" cols="8" name="reason<%=reason.getId()%>">Reply</textarea>
							<%
								number += 1;
							%>
						</div>
						<%
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
					

					
					<%} %>
					
					</table> 
					</div>
					</div>
					<br/>

					<%
						if (number > 0) {
					%>

					<input type="submit" class="btn btn-lg btn-success">
					

					<%
						}
					%>
				
			</form>
		</div>
<!-- 		 </div> 
 -->


	</c:otherwise>
</c:choose>

<c:import url="/footer.jsp"></c:import>