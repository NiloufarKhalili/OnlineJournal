<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.ResultSet"%>
<c:import url="/header.jsp">
	<c:param name="title" value="Form"></c:param>
</c:import>
<%
	ResultSet form = (ResultSet) request.getAttribute("form");
%>
<%
	String overallstr = "";
	String levelstr = "";
	String summary = "";
	String secret_message = "";
	overallstr = form.getString("overall");
	levelstr = form.getString("level");
	String statusString = "";
	statusString = form.getString("form_status");
%>
<div class="row">

	<div class="span4">
		<p>Overall Judgment</p>
		<select id="overall">
			<option value="champion"
				<%=(overallstr.equals("detractor") ? "selected" : "")%>>champion</option>
			<option value="favourable"
				<%=(overallstr.equals("detractor") ? "selected" : "")%>>favourable</option>
			<option value="indifferent"
				<%=(overallstr.equals("indifferent") ? "selected" : "")%>>indifferent</option>
			<option value="detractor"
				<%=(overallstr.equals("detractor") ? "selected" : "")%>>detractor</option>
		</select>

		<p>Level</p>
		<select id="level">
			<option value="expert"
				<%=(levelstr.equals("levelstr") ? "selected" : "")%>>expert</option>
			<option value="knowledgeable"
				<%=(levelstr.equals("knowledgeable") ? "selected" : "")%>>knowledgeable</option>
			<option value="outsider"
				<%=(levelstr.equals("outsider") ? "selected" : "")%>>outsider</option>
		</select>

		<h4>Summary</h4>
		<textarea name="summary" id="summary" rows="5" cols="10"><%=form.getString("summary")%></textarea>

		<h4>Secret Message</h4>
		<textarea name="secret" id="secret" rows="5" cols="10"><%=form.getString("secret_message")%></textarea>
		<div>
			<p>
				<input type="checkbox" name="secret_message" id="secret_message" />send
				email to editor<br />
			</p>
		</div>
		<select id="status">
			<%-- <option value="download"
				<%=(statusString.equals("download") ? "selected" : "")%>>download</option> --%>
			<option value="update"
				<%=(statusString.equals("update") ? "selected" : "")%>>update</option>
			<option value="submit"
				<%=(statusString.equals("submit") ? "selected" : "")%>>submit</option>
			<option value="accept"
				<%=(statusString.equals("accept") ? "selected" : "")%>>accept</option>
			<%-- <option value="reject"
				<%=(statusString.equals("reject") ? "selected" : "")%>>reject</option> --%>
			<option value="final reject"
				<%=(statusString.equals("final reject") ? "selected" : "")%>>final
				reject</option>
		</select>

	</div>
	<div class="span4">
		<h3>Substantive Criticism</h3>

		

		<div class="criticism" >
		<table width="80%" class="table table-striped table-hover table-borderd" >
			<thead>
				<tr>
					<th>title</th>
					<th>approve</th>
				</tr>
			</thead>
			<%
				ResultSet reason = (ResultSet) request.getAttribute("reason");
			%>
			<%
				while (reason.next()) {
			%>
			<tbody>
				<tr>
					<td><a href="${pageContext.request.contextPath}/JDBServlet?action=comment&reason_id=<%=reason.getInt("reason_id") %>" ><%=reason.getString("title")%></a></td>
					<td><button class="btn btn-primary" onclick="approveComment(<%=reason.getInt("reason_id")%>)">approve</button></td>
				</tr>
			</tbody>
			<%
				}
			%>
		</table>
			<input id="hiddenc" name="numberOfCriticism"
				style="visibility: hidden" value="" />

			<!-- <p id="myCriticism" onclick="addCriticism()">
					<span id="myCriticism" class="add-on">Add Criticism<i
						class="icon-lock"></i></span>
				</p> -->
		</div>
		<p id="myCriticism" onclick="addCriticism()">
			<span id="myCriticism" class="add-on">Add Criticism<i
				class="icon-lock"></i></span>
		</p>
	</div>
	<div class="span4">
		<h3>Errors</h3>
		<table width="80%" class="table table-striped table-hover table-borderd" >
			<thead>
				<tr>
					<th>mistake</th>
					<th>approve</th>
				</tr>
			</thead>
			<%
				ResultSet mistake = (ResultSet) request.getAttribute("mistake");
			%>
			<%
				while (mistake.next()) {
			%>
			<tbody>
				<tr>
					<td><a href="#" ><%=mistake.getString("mistake")%></a></td>
					<td><button class="btn btn-primary"  onclick="approveError(<%=mistake.getInt("mistake_id")%>)">approve</button></td>
				</tr>
			</tbody>
			<%
				}
			%>
		</table>
		<div class="errors">
			<p id="myerrors" onclick="addErrors()">
			<span id="myerrors" class="add-on">Add errors<i
				class="icon-lock"></i></span>
		</p>
		<!-- 	<h1 id="myerrors" onclick="addErrors()">Add More</h1> -->
		</div>
	</div>
</div>
<button class="btn btn-primary" type="button" onclick="createForm('${pageContext.request.contextPath}')">Submit</button>

<!-- <input type="button" class="btn" value="Submit"/> -->
<br />
<br />
<br />
<br />
<c:import url="/footer.jsp"></c:import>
