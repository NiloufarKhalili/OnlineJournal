<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/header.jsp">
 <c:param name="title" value="Instructions"></c:param>
</c:import>

<div class="hero-unit " id="container">
<h2>Instructions page</h2>
<p style="border: 1px solid #ccc; padding:15px; background-color:white;">All the submitted articles must meet the following criteria in
 order to be included in the journal. Before submitting any article,
 please read the aims and goals of the journal and also submission
 guideline carefully.</p>
<h3>Submission Guidelines</h3>
<p style="border: 1px solid #ccc; padding:15px; background-color:white;">This section describes the submission format expected for the journal, which may wary from one journal to the next.</p>
<h3>Article Templates</h3>
<div style="border: 1px solid #ccc; padding:15px; background-color:white;">
<p >The system provides two style templates(MS-word and Latex). As an author, you can download document style templates, create your articles offline using your preffered word processing system then upload your article in PDF format for review, using a web form section.</p><br>
<table class="table">
 <tr>
  <td>MS-Word style template</td>
  <td><i class="icon-download"></i>&nbsp;
  <a href="../UploadArticle?fileName=test.docx ">Download</a>
  </td>
 </tr>
 <tr>
   <td>LaTeX style template</td>
  <td><i class="icon-download"></i>&nbsp;
  <a href="../UploadArticle?fileName=LaTeX.tex ">Download</a>
  </td>
 </tr>
</table>
</div>
<h3>Aims and goals of journal</h3>

<p style="border: 1px solid #ccc; padding:15px; background-color:white;">This section explains what subject area are covered; whether the journal publishes early result; or fully mature work,etc.</p> 
<div>
 <a href="upload-article.jsp">Upload your article</a>
</div>
</div>


<c:import url="/footer.jsp"></c:import>