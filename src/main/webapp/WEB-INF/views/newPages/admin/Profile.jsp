<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Profile"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="Profile"/>
</jsp:include>
<div class="navbar-fixed-left">
    <div class="row">
        <aside class="leftside col-lg-2 col-md-2 col-sm-2 col-xs-1">
            <div class="collapse navbar-collapse" id="mobilkat">
                <ul class="nav navbar-nav navbar-dikey">
                    <li class="wet-asphalt"><a href="#">Cool</a></li>
                    <li class="wet-asphalt"><a href="#">Tab</a></li>
                    <li class="wet-asphalt"><a href="#">Menu</a></li>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">
            <jsp:include page="../includes/profile.jsp"/>
        </main>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhhghVMdW1rIbJCJupKdngdNk0k5JwaQE&libraries=places"></script>
<script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>"></script>
</body>
</html>
