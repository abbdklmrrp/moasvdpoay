<%--
  Created by Anna Rysakova
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Price in region"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/price.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="UpdatePriceInRegion"/>
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
                        <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${placesAndPrice.get(0).productId}">Product
                            info</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="wet-asphalt">
                        <a href="${pageContext.request.contextPath}/admin/getDetailsProduct?id=${placesAndPrice.get(0).productId}">Product
                            info</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:if test="${productType eq 'Tariff plan'}">
                <c:choose>
                    <c:when test="${param.page == 'ViewServicesInTariff'}">
                        <li class="wet-asphalt active-tab">
                            <a href="${pageContext.request.contextPath}/admin/viewServicesInTariff?id=${placesAndPrice.get(0).productId}">Services
                                in tariff</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="wet-asphalt">
                            <a href="${pageContext.request.contextPath}/admin/viewServicesInTariff?id=${placesAndPrice.get(0).productId}">Services
                                in tariff</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </ul>
    </div>
</div>

<div class="col-xs-1"></div>
<div class="col-xs-6">
    <form action="${pageContext.request.contextPath}/admin/updateProductPrices?id=${placesAndPrice.get(0).productId}"
          method="post">
        <div class="container">
            <div class="col-xs-12">
                <h1 style="text-align: center">Update price in regions</h1>
                <br>
                <table border="1" class="table table-striped table-hover" id="allServicesWithCategory">
                    <tr>
                        <th>№</th>
                        <th>Region</th>
                        <th>Product price</th>
                    </tr>
                    <c:forEach var="place" items="${placesAndPrice}" varStatus="placesCount">
                        <tr>
                            <td>${placesCount.count}</td>
                            <td><input type="hidden" name="placeId" value="${place.placeId}">${place.placeName}
                            </td>

                            <td>
                                    <%--<div class="form-group row">--%>
                                <div class="col-sm-8">
                                    <input type="number" class="currency"
                                           value="${place.priceProduct eq null? 0 : place.priceProduct}.00"
                                           pattern="[0-9]+([,\.][0-9]+)?" step="0.01"
                                           id="basePrice" name="priceByRegion">
                                    <i class="fa fa-user"></i>
                                </div>
                                    <%--</div>--%>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="row " id="save-price-in-region">
                    <div class="col-xs-2"></div>
                    <form action="${pageContext.request.contextPath}/admin/viewProductPriceInRegions?id=${placesAndPrice.get(0).productId}">
                        <button type="submit" class="btn btn-danger col-xs-3"
                                id="btn-cancel-price-in-region">
                            Cancel
                        </button>
                    </form>
                    <div class="col-xs-2"></div>
                    <button type="submit" class="btn btn-success col-xs-3"
                            id="btn-save-price-in-region">Save
                    </button>
                    <div class="col-xs-2"></div>
                </div>
            </div>
        </div>
    </form>
    <h2 style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</h2>
    <br>
</div>
<div class="col-xs-3"></div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="<c:url value="/resources/js/price.js"/>"></script>
<script>
    document.getElementById('basePrice').onkeypress = function (e) {
        if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') { // позволяет ввести или одну точку, или одну запятую
            return !(/[.,А-Яа-яA-Za-z-"+"]/.test(String.fromCharCode(e.charCode)));
        }
    }
</script>
</body>
</html>