<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yuliya Pedash
  Date: 27.04.2017
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Service</title>
</head>
<body>
<div align = "center" style="margin: 20px;">
<table border="1">
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Duration(days)</th>
        <th>Status</th>
    </tr>
    <c:forEach var="categoriesProducts" items="${categoriesProducts}">
    <tr>
        <td colspan="4"><strong> ${categoriesProducts.key}</strong></td>
    </tr>
    <c:forEach var="productAndStatus" items="${categoriesProducts.value}">
    <tr>
        <td>${productAndStatus.key.name}</td>
        <td>${productAndStatus.key.description}</td>
        <td>${productAndStatus.key.durationInDays}</td>
        <td>${productAndStatus.value}</td>
    </tr>
    </c:forEach>
    </c:forEach>
</table>
</div>
</body>
</html>
