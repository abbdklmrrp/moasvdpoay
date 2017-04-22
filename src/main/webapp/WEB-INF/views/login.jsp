<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/loginStyle.css" rel="stylesheet"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>

</head>

<body>
<div id="bg"></div>

<form method="POST" action="<%=request.getContextPath()%>/doLogin">
    <div class="login-form">
        <h1>Login</h1>
        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Username " id="UserName" name="username">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group log-status">
            <input type="password" class="form-control" placeholder="Password" id="Password" name="password">
            <i class="fa fa-lock"></i>
        </div>
        <c:if test="${not empty error}">
            <span style="float:right ; color: #10CE88;">${error}</span>
        </c:if>
        <button type="submit" class="log-btn">Log in</button>
    </div>

</form>
<script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<%--<script src="${contextPath}/resources/js/bootstrap.min.js"></script>--%>


</body>
</html>
