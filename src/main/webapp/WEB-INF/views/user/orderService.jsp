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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div align="center" style="margin: 20px;">
    <h1>Services Catalog</h1>
    <c:choose>
    <c:when test="${not empty msg}">
        <h3>${msg}</h3>
    </c:when>
    <c:otherwise>
    <h3></h3>

    <table border="1" class="table table-striped table-hover">
        <c:if test="${not empty resultMsg}">
            <h3>${resultMsg}</h3>
        </c:if>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Duration(days)</th>
            <th>Price</th>
            <th>Statuse</th>
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
                    <td>${productRow.price.price}</td>
                    <c:choose><c:when test="${empty productRow.status}">
                        <td class="success">
                            <form method="POST" action="<%=request.getContextPath()%>/user/ordered">
                                <input type="hidden" value="${productRow.product.id}" name="productId">
                                <input type="Submit" value="Activate">
                            </form>
                        </td>
                    </c:when>
                        <c:when test="${ productRow.status== 'Active'}">
                            <td class="danger">
                                <form method="POST" action="<%=request.getContextPath()%>/user/deactivate">
                                    <input type="hidden" value="${productRow.product.id}" name="productId">
                                    <input type="Submit" value="Deactivate">
                                </form>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>${productRow.status}</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</c:otherwise>
</c:choose>
</body>
</html>
