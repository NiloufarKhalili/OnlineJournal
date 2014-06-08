<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>

<c:import url="/header.jsp">
	<c:param name="title" value="Users"></c:param>
</c:import>

<div class="hero-unit " id="container">
	<%
		String message = (String) request.getAttribute("message");
		String emessage = (String) request.getAttribute("emessage");
	%>
	<%
		if (message != null) {
	%>
	<div class="alert alert-success">
		<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>success!</strong>
		<%=message%>
	</div>

	<%
		}
	%>
	<%
		if (emessage != null) {
	%>
	<div class="alert alert-error">
		<a href="#" class="close" data-dismiss="alert">&times;</a> <strong>Error!</strong>
		<%=emessage%>
	</div>

	<%
		}
	%>
	
	
	<div class="row">
		<div class="span9">

			<h3>Users</h3>
			
		</div>
		<div class="span1">
			<button type="button" class="btn btn-primary btn-lg active" onclick="toggleUserForm()" id="togglebtn">Invite New Editor</button>
			
			
		</div>
	</div>
	<hr />
	
	<div id="toggle1">
		<table
			class="table table-hover .table-striped .table-bordered .table-condensed">
			<tr>
				<th>Email</th>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Role</th>
				<th>Invite</th>
			</tr>
			<%
				ArrayList<User> users = (ArrayList<User>) request
						.getAttribute("users");
				User currentUser = (User) request.getSession().getAttribute("user");
			%>

			<%
				for (User user : users) {
			%>
			<%
				if (currentUser.getEmail().equals(user.getEmail())) {
			%>
			<tr style="background-color: #99FF33">
				<%
					} else {
				%>
			
			<tr style="background-color: #EBF7FB">
				<%
					}
				%>

				<td><%=user.getEmail()%></td>
				<td><%=user.getFirstname()%></td>
				<td><%=user.getLastname()%></td>
				<td><%=user.getRole()%></td>
				<%
					if (!user.getRole().equals("editor")) {
				%>
				<td><a
					href="${pageContext.request.contextPath}/EditorController?action=invite&&userId=<%=user.getId()%>"
					class="btn btn-primary"><i class="icon-envelope"></i></a></td>
				<%
					} else {
				%>
				<td></td>
				<%
					}
				%>

			</tr>
			<%
				}
			%>
		</table>
	</div>













	<div class="container" id="toggle2" style="display:none">
		<form name="signup" method="post" id=""
			action="${pageContext.request.contextPath}/EditorServiceController"
			onsubmit="return validateInvitationForm();">
			<h3>Editor Inviation</h3>
			<input type="hidden" name="hiddenMessage" value="invitation" >
			

<div id="firstnamediv"> 
			<div class="input-prepend" id="firstnamediv">
				<span class="add-on"><i class="icon-user"></i></span> <input
					type="text" name="firstname" placeholder="Firstname" value="" id="firstname">
			</div>
			</div>
			
			<div id="lastnamediv">
			<div class="input-prepend" >
				<span class="add-on"><i class="icon-user"></i></span> <input
					type="text" name="lastname" placeholder="Lastname" value="" id="lastname">
			</div>
	</div>
			
<div id="emaildiv">
			<div class="input-prepend" id="emaildiv">
				<span class="add-on"><i class="icon-envelope"></i></span> <input
					type="text" name="email" placeholder="Email" value="" id="email">
			</div>
			</div>
			<div id="messageidv">

			<div id="" >
				<textarea rows="10" cols="10" id="message" name="message"
					placeholder="your message"></textarea>

			</div>
			</div>
 <input class="btn btn-primary" type="submit"
							value="Send Invitation" id="">			

			

			

		</form>
	</div>


</div>

<c:import url="/footer.jsp"></c:import>