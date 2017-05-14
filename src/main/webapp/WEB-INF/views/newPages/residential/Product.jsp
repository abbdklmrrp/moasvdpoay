<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="${product.name}"/>
    </jsp:include>
</head>
<body>
<div class="row">
    <jsp:include page="../includes/product.jsp"/>
    <a href="<%=request.getContextPath()%>/residential/orders" method="get" class="btn btn-info"> <span
            class="glyphicon glyphicon-menu-left"></span> Back</a>
</div>
<jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>
