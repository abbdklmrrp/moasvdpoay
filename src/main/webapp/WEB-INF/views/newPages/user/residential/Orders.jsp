<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../includes/head.jsp">
        <jsp:param name="tittle" value="Orders"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="../../includes/headers/residentialHeader.jsp"/>
    <jsp:include page="../../includes/orders.jsp"/>
    <jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
