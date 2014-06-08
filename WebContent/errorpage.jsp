
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/header.jsp">
<c:param name="title" value="UserGuide"></c:param>
</c:import>

<div class="container">
  <div class="row">
    <div class="span12">
      <div class="hero-unit center">
          <h1>Page Not Found <small><font face="Tahoma" color="black">Error 404</font></small></h1>
          <br />
          <p>The page you requested could not be found</p>
          
          <a href="${pageContext.request.contextPath}/home.jsp" class="btn btn-large btn-info"><i class="icon-home icon-white"></i> Take Me Home</a>
        </div>
        <br />
     
    </div>
  </div>
</div>



<c:import url="/footer.jsp"></c:import>