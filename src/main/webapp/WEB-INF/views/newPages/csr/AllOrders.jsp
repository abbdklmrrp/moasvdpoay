<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="All orders"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/csrHeader.jsp">
    <jsp:param name="All orders" value=""/>
</jsp:include>
<jsp:include page="../includes/allOrders.jsp"/>
</body>
</html>
