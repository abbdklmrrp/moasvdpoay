<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Services"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/businessHeader.jsp">
        <jsp:param name="pageName" value="Services"/>
    </jsp:include>
    <jsp:include page="../includes/services.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
</body>
</html>