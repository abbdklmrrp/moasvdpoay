<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Registration"/>
    </jsp:include>
</head>
<%-- action="${pageContext.request.contextPath}/userRegistration" modelAttribute="user" method="post"--%>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="registration"/>
</jsp:include>
<br>
<br>
<jsp:include page="includes/registrationForm.jsp"/>
<jsp:include page="includes/footer.jsp"/>
<script>
    window.onload = function () {
        var result = "${msg}";
        if (result != '') {
            sweetAlert(result);
        }
    };
</script>
</body>
</html>
