<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="${product.name}"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/businessHeader.jsp">
    <jsp:param name="pageName" value="Products"/>
</jsp:include>
    <jsp:include page="../includes/product.jsp"/>
<%--<div class="row">--%>
<%--<a href="<%=request.getContextPath()%>/business/orders" method="get" class="btn btn-info"> <span--%>
<%--class="glyphicon glyphicon-menu-left"></span> Back</a>--%>
<%--<jsp:include page="../includes/footer.jsp"/>--%>
</body>
</html>
