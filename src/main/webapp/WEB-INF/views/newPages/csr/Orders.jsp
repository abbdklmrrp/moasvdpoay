<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="User orders"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/csrHeader.jsp">
        <jsp:param name="pageName" value="UserOrders"/>
    </jsp:include>
    <div class="col-md-2">
        <jsp:include page="../includes/csrTabMenuBegin.jsp">
            <jsp:param name="page" value="UserOrders"/>
        </jsp:include>
    </div>
    <jsp:include page="../includes/orders.jsp"/>
</body>
</html>