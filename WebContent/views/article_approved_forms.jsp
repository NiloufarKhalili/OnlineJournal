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
	 <!--  <th>created at</th> -->
	  <th>download</th>
	  <th>status</th>
	  <th>review</th>
	  <th>delete</th>
	  
 </tr>
	  </thead>
 <%
  System.out.println("success");
  ResultSet articles = (ResultSet) request.getAttribute("article");
 %>
 <%
  while (articles.next()) {
	  String status = articles.getString("form_status");
	  System.out.println("form_status is "+status); 
 %>
 <tbody>
 <tr>
  <td><%=articles.getString("title")%></td>
  <td><%=articles.getString("abstract")%></td>
  <td><%=articles.getString("first_name")+" "+articles.getString("last_name") %></td>
 <%--  <td><%=articles.getString("created_at")%></td> --%>
  <td><p onclick="download('<%=articles.getInt("id")%>','${pageContext.request.contextPath}/resources/<%=articles.getString("pdf_path")%>')"><a><%=articles.getString("pdf_path")%></a></p></td>
  <td><%=articles.getString("form_status")%></td> 
  <td>
  <% if(articles.getString("form_status").equals("download")||articles.getString("form_status").equals("update")||articles.getString("form_status").equals("accept")){
	  out.println("<a href='JDBServlet?article_id="+articles.getInt("id")+"&action=get_form'>review</a>");
  }%> 
 
 <%--  <a href="${pageContext.request.contextPath}/JDBServlet?article_id=<%=articles.getString("id")%>&action=get_form">review</a> --%>
  </td>
  <td>
 <% if(status.equals("select")){
	out.println("<button id='delete' class='btn btn-primary' onclick=deleteBtn("+articles.getInt("id")+",'await')>delete</button>");
 }%>
       
</td>
 </tr>
  </tbody>
 <%
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