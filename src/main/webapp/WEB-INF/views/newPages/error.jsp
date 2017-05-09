<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Error "/>
    </jsp:include>
</head>
<body>
<div class="container"><h1>Sorry, something went wrong :(</h1></div>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>
