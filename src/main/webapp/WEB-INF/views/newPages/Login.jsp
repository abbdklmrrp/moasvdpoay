<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Login "/>
    </jsp:include>
    <script>
        window.onload = function () {
            if (document.getElementById('message').innerHTML.trim() != '') {
                swal(document.getElementById('message').innerHTML);
            }
        };
    </script>
</head>
<body>
    <jsp:include page="includes/headers/emptyHeader.jsp"/>
    <jsp:include page="includes/loginForm.jsp"/>
    <jsp:include page="includes/footer.jsp"/>
    <h2 style="text-align: center" id="message" hidden disabled="true">${msg}</h2>
</body>
</html>