<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 9.05.2017
  Time: 9:28
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fill in price</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <script src="<c:url value="/resources/js/categoryService.js"/>"></script>
</head>
<body>

<form method="post" modelAttribute="priceByRegionDto" action="${pageContext.request.contextPath}/admin/fillTariffsPrices">

    <div class="login-form">
        <h1>ENTER PRICE TO REGION OR LEFT EMPTY</h1><br>

        <div class="form-group ">
            <select name="productId" class="form-control" id="productId">
                <c:forEach var="product" items="${tariffs}">
                    <option value="${product.id}">${product.name}</option>
                </c:forEach>
            </select>
        </div>
            <table cellpadding="5" border="1" bgcolor="#bdb76b">
                <tr>
                    <th>Place</th>
                    <th>Confirm request</th>
                    <th>Complete time</th>
                </tr>
                <c:forEach var="place" items="${placesForFillInTariff}">
                    <tr>
                        <td>${place.name}</td>
                        <td><input type="checkbox" name="placeId" value="${place.id}">check</td>
                        <td><input type="text" name="priceByRegion"></td>
                    </tr>
                </c:forEach>
            </table>
            <br>
        <button type="submit" class="log-btn" id="submit">Confirm</button>

    </div>
</form>
</body>
</html>

