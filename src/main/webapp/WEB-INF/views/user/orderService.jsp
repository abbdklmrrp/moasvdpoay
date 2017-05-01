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
<div align="center" style="margin: 20px;">
    <h1>Product Catalog</h1>
    <c:choose>
    <c:when test="${msg} != null">
        <h3>${msg}</h3>
    </c:when>
    <c:otherwise>

    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Duration(days)</th>
            <th>Status</th>
            <th>Price</th>
        </tr>
        <c:forEach var="categoriesProducts" items="${categoriesProducts}">
            <tr>
                <td colspan="5"><strong> ${categoriesProducts.key}</strong></td>
            </tr>
            <c:forEach var="productRow" items="${categoriesProducts.value}">
                <tr>
                    <td>${productRow.product.name}</td>
                    <td>${productRow.product.description}</td>
                    <td>${productRow.product.durationInDays}</td>
                    <td>${productRow.status}</td>
                    <td>${productRow.price.price}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</c:otherwise>
</c:choose>
</body>
</html>
