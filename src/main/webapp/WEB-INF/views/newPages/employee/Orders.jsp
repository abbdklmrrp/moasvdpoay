<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Orders"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/employeeHeader.jsp">
        <jsp:param name="pageName" value="Orders"/>
    </jsp:include>
    <jsp:include page="../includes/empOrdersView.jsp"/>
</body>
</html>