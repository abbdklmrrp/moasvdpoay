<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h1 style="text-align: center">Tariffs Catalog</h1>
        <br>
        <div class="col-md-12">
            <c:if test="${empty currentTariff}">
                <h4 id="currentUserTariff">No tariff now</h4>
            </c:if>
            <c:if test="${not empty currentTariff}">
                <h4 id="currentUserTariff">Current tariff: ${currentTariff.name}</h4>
            </c:if>
        </div>
        <table border="1" class="table table-striped table-hover" id="tableServiceCatalog">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Duration(days)</th>
                <th>Price($)</th>
                <th>Services</th>
                <th>Status</th>
            </tr>
            <c:forEach var="tariff" items="${tariffs}">
                <tr>
                    <td class="${tariff.id}">${tariff.name}</td>
                    <td>${tariff.description}</td>
                    <td>${tariff.durationInDays}</td>
                    <td>${tariff.basePrice}</td>
                    <td><input type="button" onclick="showServicesOfTariff(${tariff.id})" value="Show"
                               class="btn btn-primary"></td>
                    <c:choose>
                        <c:when test="${ tariff.id == currentTariff.id}">
                            <td id="${tariff.id}"><input type="button" name="${tariff.id}"
                                                         onclick="deactivateTariff(${tariff.id}, ${userId})"
                                                         value="Deactivate" class="btn btn-danger"></td>
                        </c:when>
                        <c:otherwise>
                            <td id="${tariff.id}"><input type="button" name="${tariff.id}"
                                                         onclick="activateTariff(${tariff.id}, ${userId}, ${currentTariff.id})"
                                                         value="Activate" class="btn btn-success"></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-2"></div>
</div>