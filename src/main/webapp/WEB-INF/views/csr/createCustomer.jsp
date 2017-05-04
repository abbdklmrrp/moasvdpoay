<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 26.04.2017
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Customer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
</head>
<body>
<form action="${contextPath}/csr/createCustomer" modelAttribute="customer" method="post">
    <div class="login-form">
        <h2>Create new customer</h2>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Company name " id="CompanyName" name="companyName">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="password" class="form-control" placeholder="Secret key" id="SecretKey" name="secretKey">
                <i class="fa fa-lock"></i>
            </div>
        <button type="submit" class="log-btn">Create</button>
    </div>
</form>

</body>
</html>
