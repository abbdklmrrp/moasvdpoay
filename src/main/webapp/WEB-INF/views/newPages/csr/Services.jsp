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
    <jsp:param name="pageName" value="UserServices"/>
</jsp:include>
<jsp:include page="../includes/services.jsp"/>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
