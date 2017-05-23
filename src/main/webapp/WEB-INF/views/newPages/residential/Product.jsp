<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="${product.name}"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/residentialHeader.jsp">
    <jsp:param name="pageName" value="Products"/>
</jsp:include>
<jsp:include page="../includes/product.jsp"/>
</body>
</html>
