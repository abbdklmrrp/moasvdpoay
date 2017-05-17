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
<jsp:include page="../includes/footer.jsp"/>

<div class="navbar-fixed-left">
    <div class="row">
        <aside class="leftside col-lg-2 col-md-2 col-sm-2 col-xs-1">
            <div class="collapse navbar-collapse" id="mobilkat">
                <ul class="nav navbar-nav navbar-dikey">
                    <c:choose>
                        <c:when test="${param.page == 'ProductInfo'}">
                            <li class="wet-asphalt active-tab">
                                <a href="${pageContext.request.contextPath}/admin/getProducts">All products</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt">
                                <a href="${pageContext.request.contextPath}/admin/getProducts">All products</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${param.page == 'Products'}">
                            <li class="wet-asphalt active-tab">
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct=${servicesByTariff.get(0).tariffId}">Product
                                    info</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt">
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct=${servicesByTariff.get(0).tariffId}">Product
                                    info</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${customerType eq 'Residential'}">
                        <c:choose>
                            <c:when test="${param.page == 'UpdatePriceInRegion'}">
                                <li class="wet-asphalt active-tab">
                                    <a href="${pageContext.request.contextPath}/admin/updateProductPrice=${servicesByTariff.get(0).tariffId}">Price
                                        in regions</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="wet-asphalt">
                                    <a href="${pageContext.request.contextPath}/admin/updateProductPrice=${servicesByTariff.get(0).tariffId}">Price
                                        in regions</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">
            <form action="${pageContext.request.contextPath}/admin/updateServicesInTariff=${servicesByTariff.get(0).tariffId}"
                  method="post">
                <div class="container">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <h1 style="text-align: center">Fill in tariff with services</h1>

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
        </main>
    </div>
</div>