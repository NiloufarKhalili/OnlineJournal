<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/header.jsp">
	<c:param name="title" value="UploadPage"></c:param>
</c:import>
<%   
    String message = (String) request.getAttribute("message");
	if (message != null) {
%>
<div class="alert alert-error">
	<a href="#" class="close" data-dismiss="error">&times;</a> <strong>Oops!</strong>
	<%=message%>
	<br />
</div>
<%
	}
%>

<h1>Upload your article</h1>
<hr/>
<div class="hero-unit " id="container">
 <form class="form" action="${pageContext.request.contextPath}/UploadArticle" method="post"
		enctype="multipart/form-data" onsubmit="return validate();">
	<!-- <form class="form" action="" method="post"
		enctype="multipart/form-data" onsubmit="return go();"> -->

		<legend>Form For Submitting Article</legend>
		<div id="firstnamediv">
			<div style="width: 200px; display: inline-block">
				<b>Firstname:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-user"></i></span> <input
					type="text" size="30" id="firstname" name="firstname" value=""
					placeholder="your firstname" />
			</div>
		</div>
		<div id="lastnamediv">
			<div style="width: 200px; display: inline-block">
				<b>Lastname:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-user"></i></span> <input
					type="text" size="30" name="lastname" value=""
					placeholder="your lastname" id="lastname" />
			</div>
		</div>
		<div id="emaildiv">
			<div style="width: 200px; display: inline-block">
				<b>Email:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-envelope"></i></span> <input
					type="text" size="30" name="email" value="" placeholder="Email"
					id="email" />
			</div>
		</div>
		<div id="affiliationdiv">
			<div style="width: 200px; display: inline-block">
				<b>Affiliation:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-pencil"></i></span> <input
					type="text" size="30" name="affiliation" value="" placeholder="Affiliation"
					id="affiliation" />
			</div>
		</div>
		
		
		
		
		
		<!-- 
		
		<div id="author" onclick="addAuthor()">
			<span id="author" class="add-on"><b>Add more author</b><i
				class="icon-plus"></i></span>
		</div>
		
		 -->
		
		
		<div class="add-remove-containor">
  	<a onclick="addAuthor();" href="#" class="btn btn-default btn-lg" id="add-author-btn">
	  <i class="icon-plus-sign"></i> Add Author
	</a>
	<a href="#" type="button" class="btn btn-default btn-lg " disabled="disabled" id="remove-author-btn" onclick="removeAuthor();">
	  <span class="icon-minus-sign"></span> Remove Author
	</a>
  </div>
		
		
		
		
		

		<div class="authors" style="position: fixed, top:30px, right:5px">
			<input type="text" id="hiddenc" name="numberOfAuthors"
				style="visibility: hidden" value="0">
		</div>
		

		<div id="titlediv">
			<div style="width: 200px; display: inline-block">
				<b>Title:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-pencil"></i></span> <input
					type="text" size="30" name="title" id="title" value=""
					placeholder="Title" />
			</div>
		</div>
		<div id="absdiv">
			<div style="width: 200px; display: inline-block">
				<b>Abstract:</b>
			</div>
			<div style="width: 250px; display: inline-block">
				<textarea rows="12" cols="25" name="abstract" style="width:234px;"
					placeholder="Enter Maximum 250" id="abs"></textarea>
			</div>
		</div>

		<div id="keywordsdiv">
			<div style="width: 200px; display: inline-block">
				<b>Keywords:</b>
			</div>
			<div class="input-prepend"
				style="width: 250px; display: inline-block">
				<span class="add-on"><i class="icon-pencil"></i></span> <input
					type="text" size="30" name="keywords" value="" id="keywords" style="width:500px;" 
					placeholder="Enter Maximum 10 keywords seperated by comma" />
			</div>
		</div>
		<div id="filediv">

			<div style="width: 200px; display: inline-block">
				<b>Upload your article:</b>
			</div>
			<div style="width: 250px; display: inline-block">
				<input size="30" type="file" name="file" id="file" />
			</div>
		</div>

			<br> <br>
			<div>
				<button class="btn btn-primary btn-large" type="submit">Upload</button>

			</div>
		
	</form>
</div>

<c:import url="/footer.jsp"></c:import>













