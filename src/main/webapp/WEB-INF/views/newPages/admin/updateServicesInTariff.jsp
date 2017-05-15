<%--
  Created by IntelliJ IDEA.
  User: Anna Rysakova
  Date: 15.05.2017
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Update services in tariff"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="UpdateServicesInTariff"/>
</jsp:include>
<form action="/admin/updateServicesInTariff" method="post">
    <div class="container">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1 style="text-align: center">Fill in tariff with services</h1>
            <br>
            <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                <tr>
                    <th>Category</th>
                    <th>Services</th>
                    <th>Services in tariff</th>
                </tr>
                <c:forEach var="servcesByCategory" items="${allServicesWithCategory}">
                    <tr>
                        <td>${servcesByCategory.key}</td>
                        <td>
                            <select name="selectedService">
                                <option value="">-</option>
                                <c:forEach var="product" items="${servcesByCategory.value}">
                                    <c:forEach var="service" items="${servicesByTariff}">
                                        <c:if test="${service.serviceId eq product.id}">
                                            <option value="${product.id}" selected>${product.name}</option>
                                        </c:if>
                                    </c:forEach>
                                    <option value="${product.id}">${product.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <c:forEach var="service" items="${servicesByTariff}">
                                <c:if test="${servcesByCategory.key eq service.categoryName}">
                                    <span>${service.serviceName}</span> </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="row">
                <div class="col-sm-4 col-xs-0"></div>
                <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Update</button>
                <div class="col-sm-4 col-xs-0"></div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
</form>