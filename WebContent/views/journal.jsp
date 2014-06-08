
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/header.jsp">
	<c:param name="title" value="UserGuide"></c:param>
</c:import>
<div class="hero-unit " id="container">

	<h3>Setup Journal</h3>
	<hr>



	<form class="form" name="reader" method="post"
		action="${pageContext.request.contextPath}/ReaderController"
		onsubmit="return validateJournal()">



		<div id="titlediv">
			<label><b>Title</b></label> <input style="width: 300px" type="text"
				placeholder="title" id="title" name="title">
		</div>

		<div id="aimdiv">
			<label><b>Aims & Goals</b></label>
			<textarea rows="10" style="width: 300px" id="aim" name="aim"
				placeholder="your aims&goals"></textarea>

		</div>
		<div id="guidediv">
			<label><b>Submission Guidelines</b></label>
			<textarea rows="10" cols="19" style="width: 300px;" id="guide"
				name="guide" placeholder="submission guidelines"></textarea>

		</div>
		<div id="fdiv">

			<div style="width: 200px; display: inline-block">
				<b>Upload template:</b>
			</div>
			<div style="width: 250px; display: inline-block">
				<input size="30" type="file" name="file" id="file" />
			</div>
		</div>

		<input class="btn btn-primary" type="submit" value="Submit" id="">


	</form>

</div>
<c:import url="/footer.jsp"></c:import>

