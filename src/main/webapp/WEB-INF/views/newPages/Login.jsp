<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Login "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/aboutHeader.jsp">
    <jsp:param name="pageName" value="login"/>
</jsp:include>
<br>
<br>
<jsp:include page="includes/loginForm.jsp"/>
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