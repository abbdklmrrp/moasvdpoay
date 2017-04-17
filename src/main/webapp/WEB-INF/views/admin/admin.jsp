<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin</title>
</head>
<body bgcolor="#f0f8ff">
<div align="center">
    ${user.username} page!
    <a href="<%=request.getContextPath()%>/doLogout">Logout</a>
</div>
</body>
</html>