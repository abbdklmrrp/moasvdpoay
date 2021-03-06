<%--
  Created by Anna Rysakova
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Create service"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/price.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="FillTariffPrices"/>
</jsp:include>
<jsp:include page="../includes/footer.jsp"/>
<form modelAttribute="priceByRegionDTO"
      action="${pageContext.request.contextPath}/admin/fillProductPrices/${id}" method="post">
    <div class="container">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Fill in tariff with price by region</h1>
            <br>
            <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                <tr>
                    <th>№</th>
                    <th>Region</th>
                    <th>Product price</th>
                </tr>
                <c:forEach var="place" items="${placesForFillInTariff}" varStatus="placeCount">
                    <tr>
                        <td>${placeCount.count}</td>
                        <td><input type="hidden" name="placeId" value="${place.id}">${place.name}</td>
                        <td>
                            <div class="form-group row">
                                <div class="col-sm-8">
                                    <input type="number" class="currency" value="0.00"
                                           pattern="[0-9]+([,\.][0-9]+)?" step="0.01"
                                           name="priceByRegion">
                                    <i class="fa fa-user"></i>
                                </div>
                            </div>
                        </td>
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
<script type="text/javascript" src="<c:url value="/resources/js/price.js"/>"></script>