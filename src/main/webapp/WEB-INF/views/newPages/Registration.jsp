<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Registration"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="includes/headers/emptyHeader.jsp"/>
    <jsp:include page="includes/registrationForm.jsp"/>
    <jsp:include page="includes/footer.jsp"/>
</body>
</html>