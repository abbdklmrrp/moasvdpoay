<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../includes/head.jsp">
        <jsp:param name="tittle" value="Tariffs"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../../includes/headers/residentialHeader.jsp"/>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h1 style="text-align: center">Tariffs Catalog</h1>
        <br>
        <table border="1" class="table table-striped table-hover" id="tableServiceCatalog">
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
                    <td><input type="button" onclick="showServicesOfTariff(${tariff.id})" value="Show"></td>
                    <c:choose>
                        <c:when test="${ tariff.id == currentTariff.id}">
                            <td id="${tariff.id}"><input type="button" onclick="deactivateTariff(${tariff.id})" value="Deactivate" class="btn btn-danger"></td>
                        </c:when>
                        <c:otherwise>
                            <td id="${tariff.id}"><input type="button" onclick="activateTariff(${tariff.id})" value="Activate" class="btn btn-success"></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-2"></div>
</div>
<jsp:include page="../../includes/footer.jsp"/>
</body>
</html>
