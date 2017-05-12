<%--
  Created by IntelliJ IDEA.
  User: Yuliya Pedash
  Date: 12.05.2017
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../includes/head.jsp">
        <jsp:param name="tittle" value="${product.name}"/>
    </jsp:include>
</head>
<body>
<div class="row">
    <jsp:include page="../../includes/product.jsp"/>
    <jsp:include page="../../includes/footer.jsp"/>
    <a href="<%=request.getContextPath()%>/user/residential/orders" method="get" class="btn btn-info"> <span
            class="glyphicon glyphicon-menu-left"></span> Back</a>
</div>
</body>
</html>
