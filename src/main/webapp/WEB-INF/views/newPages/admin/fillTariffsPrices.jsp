<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 9.05.2017
  Time: 9:28
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Create service"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="AddService"/>
</jsp:include>
<form modelAttribute="priceByRegionDto"
      action="${pageContext.request.contextPath}/admin/fillTariffsPrices" method="post">
    <div class="container">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Fill in tariff with price by region</h1>
            <br>
            <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                <tr>
                    <th>Region</th>
                    <th>Product price</th>
                </tr>
                <c:forEach var="place" items="${placesForFillInTariff}">
                    <tr>
                        <td><input type="hidden" name="placeId" value="${place.id}">${place.name}</td>
                        <td><input type="text" name="priceByRegion"></td>
                    </tr>
                </c:forEach>
            </table>
            <div class="row">
                <div class="col-sm-4 col-xs-0"></div>
                <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                <div class="col-sm-4 col-xs-0"></div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
</form>