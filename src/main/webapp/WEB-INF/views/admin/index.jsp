<%--
  Created by Rysakova Anna.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ADMIN</title>
</head>
<body bgcolor="#f0f8ff">
<div align="center">
    admin page!
    <a href="<%=request.getContextPath()%>/doLogout">Logout</a><br><br>
    <a href="${contextPath}/admin/createProduct">create new product</a>
</div>
</body>
</html>