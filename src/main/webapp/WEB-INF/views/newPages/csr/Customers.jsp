<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Customers"/>
    </jsp:include>
    <%--<script type="text/javascript">--%>
        <%--google.load("jquery", "1.4.4");--%>
    <%--</script>--%>
</head>
<body>
    <jsp:include page="../includes/headers/csrHeader.jsp">
        <jsp:param name="pageName" value="Customers"/>
    </jsp:include>
    <jsp:include page="../includes/allCustomers.jsp"/>
</body>
</html>