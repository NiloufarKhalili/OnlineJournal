<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>

<c:import url="/header.jsp">
 <c:param name="title" value="Users"></c:param>
</c:import>
<div class="hero-unit " id="container">
<form action="ArticleToSelectServlet" method="get">

<table class="table table-striped table-hover table-borderd">
	<thead>
 <tr>
 	  <th>select</th>
	  <th>title</th>
	  <th>abstract</th>
	  <th>main author</th>
	  <th>created at</th>
 </tr>
	  </thead>
 <%
  ResultSet articles = (ResultSet) request.getAttribute("article");
 %>
 <%
  while (articles.next()) {
 %>
 <tbody>
 <tr>
  <td><input type="checkbox" name="article_id" onClick="return KeepCount()" value=<%=articles.getString("id")%> /></td>
  <td><%=articles.getString("title")%></td>
  <td><%=articles.getString("abstract")%></td>
  <td><%=articles.getString("first_name")+" "+articles.getString("last_name") %></td>
  <%-- <td><a href='<%=articles.getString("pdf_path")%>'><%=articles.getString("pdf_path")%></a></td> --%>
  <td><%=articles.getString("created_at")%></td>
 </tr>
  </tbody>
 <%
 }
%>
</table>
<input class="btn btn-primary" type="submit" value="select"/>

</form>
<p align="right">
<h4 align="right" >articles in waiting list : <%=request.getAttribute("select_await_count") %></h4>
<h4 align="right">accept articles : <%=request.getAttribute("accept_count") %></h4>
<h4 align="right">reject articles : <%=request.getAttribute("final_reject_count") %></h4>
<h4 align="right" style="color:red">rejected forms : <%=request.getAttribute("delete_count") %></h4>
<p>

</div>
<c:import url="/footer.jsp"></c:import>