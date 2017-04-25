<%--
  Created by IntelliJ IDEA.
  User: Petro
  Date: 17.04.2017
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
</head>
<body>
    <form action="signUp" method="post">
        <div class="login-form">
            <h1>Sign up</h1>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="First name " id="FirstName" name="firstName">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Last Name " id="LastName" name="lastName">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Email " id="Email" name="email">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Phone number " id="Phone number" name="phoneNumber">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="City " id="City" name="city">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Street " id="Street" name="street">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group ">
                <input type="text" class="form-control" placeholder="Building " id="Building" name="building">
                <i class="fa fa-user"></i>
            </div>
            <div class="form-group log-status">
                <input type="password" class="form-control" placeholder="Password" id="Password" name="password">
                <i class="fa fa-lock"></i>
            </div>
            <button type="submit" class="log-btn">Sign up</button>
        </div>
</form>
    <script class="cssdeck" src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
</body>
</html>
