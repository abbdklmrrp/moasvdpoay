<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 09.05.2017
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/head.jsp" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="User services"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/csrHeader.jsp">
        <jsp:param name="pageName" value="Users"/>
    </jsp:include>
    <div class="col-md-2">
        <jsp:include page="../includes/csrTabMenuBegin.jsp">
            <jsp:param name="page" value="UserServices"/>
        </jsp:include>
    </div>
    <jsp:include page="../includes/services.jsp"/>
</body>
</html>
