<%--
  Created by Anna Rysakova
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
    <link href="<c:url value="/resources/css/select.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="UpdateServicesInTariff"/>
</jsp:include>

<div class="col-xs-2">
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
                        <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${id}">Product
                            info</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="wet-asphalt">
                        <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${id}">Product
                            info</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:if test="${customerType eq 'Residential'}">
                <c:choose>
                    <c:when test="${param.page == 'ViewPriceInRegion'}">
                        <li class="wet-asphalt active-tab">
                            <a href="${pageContext.request.contextPath}/admin/viewProductPriceInRegions?id=${id}">Price
                                in regions</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="wet-asphalt">
                            <a href="${pageContext.request.contextPath}/admin/viewProductPriceInRegions?id=${id}">Price
                                in regions</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </ul>
    </div>
</div>

<div class="col-xs-1"></div>
<div class="col-xs-6">
    <form action="${pageContext.request.contextPath}/admin/updateServicesInTariff?id=${id}"
          method="post">
        <div class="container">
            <div class="col-xs-12">
                <h1 style="text-align: center">Update services in tariff</h1>

                <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                    <tr>
                        <td>№</td>
                        <th>Category</th>
                        <th>Services</th>
                        <th>Services in tariff</th>
                    </tr>
                    <c:forEach var="servcesByCategory" items="${allServicesWithCategory}"
                               varStatus="allCategory">
                        <tr>
                            <td>${allCategory.count}</td>
                            <td>${servcesByCategory.key}</td>
                            <td>
                                <select name="selectedService" id="soflow">
                                    <option value=""></option>
                                    <c:if test="${servicesByTariff.size() ne 0}">
                                        <c:forEach var="service" items="${servicesByTariff}">
                                            <c:if test="${servcesByCategory.key eq service.categoryName}">
                                                <option value="${service.serviceId}"
                                                        selected>${service.serviceName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:forEach var="product" items="${servcesByCategory.value}">
                                        <c:if test="${product.id != ''}">
                                            <option value="${product.id}">${product.name}</option>
                                        </c:if>
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
                <div class="row " id="save-price-in-region">
                    <div class="col-xs-2"></div>
                    <form action="${pageContext.request.contextPath}/admin/viewServicesInTariff?id=${id}">
                        <button type="submit" class="btn btn-danger col-xs-3"
                                id="btn-cancel-price-in-region">
                            Cancel
                        </button>
                    </form>
                    <div class="col-xs-2"></div>
                    <button type="submit" class="btn btn-success col-xs-3"
                            id="btn-save-price-in-region">Update
                    </button>
                    <div class="col-xs-2"></div>
                </div>
            </div>
        </div>
    </form>
    <br>
</div>
<div class="col-xs-3"></div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>