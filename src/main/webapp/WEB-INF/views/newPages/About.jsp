<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="About "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="about"/>
</jsp:include>

<section class="container" style="align-self: center">
    <div class="col-md-4" style="align-self: center"></div>
    <div class="col-md-4">
        <div class="col-md-1" style="align-self: center"></div>
        <div class="col-md-10" style="align-self: center">
            <h1>hello</h1><br>
            <h2>we are young perspective team</h2><br>
            <h3>creating some new outstanding product</h3><br>
            <h4>new functions none have ever seen</h4><br>
            <h5>new info will be added</h5><br>
            <h6>cya</h6><br>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="col-md-4"></div>
</section>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>
