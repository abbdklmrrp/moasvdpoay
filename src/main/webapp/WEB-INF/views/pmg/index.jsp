<%--
  Created by Rysakova Anna.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>PMG</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link href="${pageContext.request.contextPath}/resources/css/basic.css" rel="stylesheet"/>
</head>
<body bgcolor="#f5f5dc">
<div class="login-form">
    <h1>PMG PAGE</h1>
    <div class="form-group "><br>
        <h2><a href="${pageContext.request.contextPath}/pmg/getProfile">Profile</a></h2><br>
        <h2><a href="<%=request.getContextPath()%>/doLogout">Logout</a></h2><br>
    </div>
</div>
</body>
</html>