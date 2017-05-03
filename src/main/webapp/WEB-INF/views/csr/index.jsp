<%--
  Created by Rysakova Anna.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CSR</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
</head>
<body>
<div class="login-form">
    <h1>CSR PAGE</h1>
    <div class="form-group "><br>
        <h2><a href="<%=request.getContextPath()%>/doLogout">Logout</a></h2><br>
        <h2><a href="${contextPath}/csr/getCreateCustomer">Create new customer</a></h2><br>
        <h2><a href="${contextPath}/csr/getWriteComplaint">Write complaint</a></h2><br>
        <h2><a href="${contextPath}/csr/getUsersPage">Users details</a></h2><br>
        <h2><a href="${contextPath}/csr/registration">Create new user</a></h2>
    </div>
</div>
</body>
</html>