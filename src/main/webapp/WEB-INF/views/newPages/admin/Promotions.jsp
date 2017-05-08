<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Promotions"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/adminHeader.jsp"/>
    <div class="container">
        <section id="error" class="container">
            <h1>Page not found</h1>
            <p>The Page in developing.</p>
            <a class="btn btn-success" href="${pageContext.request.contextPath}/admin/getProfile">GO BACK TO THE HOMEPAGE</a>
        </section>
    </div>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>
