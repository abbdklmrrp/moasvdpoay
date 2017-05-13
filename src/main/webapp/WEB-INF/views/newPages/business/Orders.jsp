<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Orders"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/businessHeader.jsp">
    <jsp:param name="pageName" value="Orders"/>
</jsp:include>
    <jsp:include page="../includes/orders.jsp"/>
    <jsp:include page="../includes/footer.jsp"/>
    <script type="text/javascript" src="<c:url value="${contextPath}/resources/js/suspendOrder.js"/>"></script>
</body>
</html>
