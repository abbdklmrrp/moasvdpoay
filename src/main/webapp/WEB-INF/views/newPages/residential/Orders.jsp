<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Orders"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../includes/headers/residentialHeader.jsp">
        <jsp:param name="pageName" value="Orders"/>
    </jsp:include>
    <jsp:include page="../includes/orders.jsp"/>

    <script type="text/javascript"
            src="<c:url value="${pageContext.request.contextPath}/resources/js/orders.js"/>"></script>
</body>
</html>
