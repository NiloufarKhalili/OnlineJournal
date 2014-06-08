<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>

<c:import url="/header.jsp">
 <c:param name="title" value="Users"></c:param>
</c:import>
<div class="hero-unit " id="container">


<table class="table table-striped table-hover table-borderd">
	<thead>
 <tr>
	  <th>title</th>
	  <th>abstract</th>
	  <th>main auther</th>
	  <th>created at</th>
	  <th>delete</th>
	 <!--  <th>status</th>
	  <th>unselect</th> -->
 </tr>
	  </thead>
 <%
  ResultSet articles = (ResultSet) request.getAttribute("article");
 %>
 <%

 
 
int rowCount = 0;
  while (articles.next()) {
	  rowCount+=1;
 %>
 <tbody> 
 <tr>
  <td><%=articles.getString("title")%></td>
  <td><%=articles.getString("abstract")%></td>
  <td><%=articles.getString("first_name")+" "+articles.getString("last_name") %></td>
  <%-- <td><a href='<%=articles.getString("pdf_path")%>'><%=articles.getString("pdf_path")%></a></td> --%>
  <td><%=articles.getString("created_at")%></td>
  <td><button id="delete" class="btn btn-primary" onclick="deleteBtn(<%=articles.getInt("id")%>,'await')">delete</button></td>
 <%--  <td><%=articles.getString("status")%></td>
  <td>
  <input type="button" class="btn" name="article_id" value=<%=articles.getString("id")%>>
  </td> --%>
 </tr>
  </tbody>
 <%
 }
  if(rowCount==0){
		out.println("<tbody><tr><td colspan='5' align='center'><h5 align='center'>Sorry, there is no result</h5></td></tr></tbody>");
	 }
%>
</table>

<p align="right">
<h4 align="right" >articles in waiting list : <%=request.getAttribute("select_await_count") %></h4>
<h4 align="right">accept articles : <%=request.getAttribute("accept_count") %></h4>
<h4 align="right">reject articles : <%=request.getAttribute("final_reject_count") %></h4>
<h4 align="right" style="color:red">rejected forms : <%=request.getAttribute("delete_count") %></h4>
<p>
</div>
<c:import url="/footer.jsp"></c:import>