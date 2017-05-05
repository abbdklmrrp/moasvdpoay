<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tariffs</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/sweet-alert.css"/>" rel="stylesheet">
</head>
<body>
    <c:if test="${not empty urrentTariff}">
        <h3>Current tariff: ${currentTariff.name}</h3>
    </c:if>

    <table border="1" class="table table-striped table-hover">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Duration(days)</th>
            <th>Price</th>
            <th>Services</th>
            <th>Status</th>
        </tr>
            <c:forEach var="tariff" items="${tariffsByPlace}">
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.description}</td>
                    <td>${tariff.durationInDays}</td>
                    <td>${tariff.basePrice}</td>
                    <td><input type="button" onclick="showServicesOfTariff(tariff.id)"></td>
                    <c:choose>
                        <c:when test="${ tariff.id == currentTariff.id}">
                            <input type="button" onclick="deactivateTariff(tariff.id)" value="Deactivate">
                        </c:when>
                        <c:otherwise>
                            <input type="button" onclick="activateTariff(tariff.id)" value="Activate">
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
    </table>

    <script src="${contextPath}/resources/js/serviceTariff.js"></script>
</body>
</html>
