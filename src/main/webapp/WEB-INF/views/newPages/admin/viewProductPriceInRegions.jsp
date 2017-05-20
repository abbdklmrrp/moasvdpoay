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
        <jsp:param name="tittle" value="Price in region"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="UpdatePriceInRegion"/>
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
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${priceInRegionsByProduct.get(0).productId}">Product
                                    info</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="wet-asphalt">
                                <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${priceInRegionsByProduct.get(0).productId}">Product
                                    info</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${productType eq 'Tariff'}">
                        <c:choose>
                            <c:when test="${param.page == 'UpdateServicesInTariff'}">
                                <li class="wet-asphalt active-tab">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff?id=${priceInRegionsByProduct.get(0).productId}">Services
                                        in tariff</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="wet-asphalt">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff?id=${priceInRegionsByProduct.get(0).productId}">Services
                                        in tariff</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">

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
                        <c:forEach var="place" items="${priceInRegionsByProduct}">
                            <tr>
                                <td>${place.placeName} </td>
                                <td>
                                    <div class="form-group row">
                                        <div class="col-sm-8">
                                                ${place.priceProduct}
                                            <i class="fa fa-user"></i>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%--<div class="row">--%>
                    <%--<div class="col-sm-4 col-xs-0"></div>--%>
                    <%--<button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>--%>
                    <%--<br><br>--%>
                    <%--<div class="col-sm-4 col-xs-0"></div>--%>
                    <%--</div>--%>
                    <div class="row hide" id="save-price-in-region">
                        <button type="button" class="btn btn-danger col-sm-5 col-xs-5"
                                id="btn-cancel-price-in-region">
                            Cancel
                        </button>
                        <div class="col-sm-2 col-xs-2"></div>
                        <button type="submit" class="btn btn-success col-sm-5 col-xs-5"
                                id="btn-save-price-in-region">Save
                        </button>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>
            <form method="get"
                  action="${pageContext.request.contextPath}/admin/updateProductPrice/${priceInRegionsByProduct.get(0).productId}">
                <div class="row" id="edit-and-changes-price-in-region">
                    <div class="col-sm-4 col-xs-0"></div>
                    <button class="btn btn-primary col-sm-5 col-xs-5" id="btn-edit-price-in-region-info">
                        Update price
                    </button>
                    <div class="col-sm-4 col-xs-0"></div>
                </div>
            </form>
            <h2 style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</h2>
        </main>
    </div>
</div>
