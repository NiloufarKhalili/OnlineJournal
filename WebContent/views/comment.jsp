<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="beans.Comment"%>
<%@ page import="java.util.List"%>
<c:import url="/header.jsp">
	<c:param name="title" value="Users"></c:param>
</c:import>
<div class="hero-unit " id="container">
	<%
		//TODO display exist comment
	%>
	<%-- <% List<Comment> comments = ( List<Comment> ) request.getAttribute("commentList"); 
   System.out.println(comments.size());
%>
 --%>
	<c:forEach items="${requestScope.commentList}" var="comment">
		<div class="col-lg-4">
			<c:if test="${comment.parent_id==-1}">
				<h2>
					Reason Title :
					<c:out value="${comment.title}" />
				</h2>
			</c:if>
			<blockquote>
				<p class="text-danger">
					Comment content :
					<textarea style="background-color: white;" rows="8" cols="8"
						disabled="disabled"><c:out value="${comment.content}" /></textarea>
				</p>
			</blockquote>
		</div>
	</c:forEach>
</div>

<div class="span4">
	<h3>Comment</h3>
	<textarea id="content" rows="5" cols="8"></textarea>
	<br>
	<button class="btn btn-primary" onclick="submitBtn()">submit</button>


</div>
<c:import url="/footer.jsp"></c:import>