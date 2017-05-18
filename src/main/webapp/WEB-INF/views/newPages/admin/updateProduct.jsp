<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 23.04.2017
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="ProductInfo"/>
    </jsp:include>
    <title>Product info</title>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="ProductInfo"/>
</jsp:include>
<div class="navbar-fixed-left">
    <div class="row">
        <aside class="leftside col-lg-2 col-md-2 col-sm-2 col-xs-1">
            <div class="collapse navbar-collapse" id="mobilkat">
                <ul class="nav navbar-nav navbar-dikey">
                    <c:choose>
                        <c:when test="${param.page == 'Products'}">
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
                    <c:if test="${product.customerType eq 'Residential'}">
                        <c:choose>
                            <c:when test="${param.page == 'UpdatePriceInRegion'}">
                                <li class="wet-asphalt active-tab">
                                    <a href="${pageContext.request.contextPath}/admin/viewProductPriceInRegions?id=${product.id}">Price
                                        in regions</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="wet-asphalt">
                                    <a href="${pageContext.request.contextPath}/admin/viewProductPriceInRegions?id=${product.id}">Price
                                        in regions</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${product.productType eq 'Tariff'}">
                        <c:choose>
                            <c:when test="${param.page == 'UpdateServicesInTariff'}">
                                <li class="wet-asphalt active-tab">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff=${product.id}">Services
                                        in tariff</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="wet-asphalt">
                                    <a href="${pageContext.request.contextPath}/admin/updateServicesInTariff=${product.id}">Services
                                        in tariff</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </ul>
            </div>
        </aside>
        <main class="col-lg-10 col-md-10 col-sm-10 col-xs-11">
            <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
                <form method="POST" modelAttribute="product"
                      action="${pageContext.request.contextPath}/admin/updateProduct=${product.id}">
                    <div class="login-form">
                        <h1 style="text-align: center">Product info</h1>
                        <c:if test="${not empty error}">
                            <span style="float:right ; color: #10CE88;">${error}</span>
                        </c:if>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Name</label>
                            <div class="col-sm-8">
                                <input readonly type="text" class="form-control" placeholder="Name " id="Name"
                                       name="name" value="${product.name}" required>
                                <i class="fa fa-user"></i>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Description</label>
                            <div class="col-sm-8">
                                <input readonly type="text" class="form-control" placeholder="Description "
                                       id="Description" name="description" value="${product.description}" required>
                                <i class="fa fa-user"></i>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Base price</label>
                            <div class="col-sm-8">
                                <input readonly type="number" class="form-control" value="${product.basePrice}"
                                       id="basePrice" name="basePrice" onchange="handlePrice(this)" required>
                                <i class="fa fa-user"></i>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Customer type</label>
                            <div class="col-sm-8">
                                <select disabled name="customerType" class="form-control" id="customerTypeId">
                                    <option value="Business" ${product.customerType=='Business'? 'selected="selected"' : ''}>
                                        Business
                                    </option>
                                    <option value="Residential" ${product.customerType=='Residential'? 'selected="selected"' : ''}>
                                        Residential
                                    </option>
                                </select></div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Duration in days</label>
                            <div class="col-sm-8">
                                <input readonly type="number" name="durationInDays" id="durationInDays"
                                       class="form-control"
                                       placeholder="365"
                                       onchange="handleChange(this)"
                                       value="${product.durationInDays}">
                                <i class="fa fa-user"></i>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Need processing</label>
                            <div class="col-sm-8">
                                <div class="row">
                                    <input disabled type="radio" name="processingStrategy" id="processingStrategy"
                                           class="col-sm-1"
                                           value="NeedProcessing" ${product.processingStrategy=='NeedProcessing'? 'checked="checked"' : ''}>
                                    <label class="col-sm-5 control-label">Yes</label>
                                    <input disabled type="radio" name="processingStrategy" id="notProcessingStrategy"
                                           class="col-sm-1" value="DoNotNeedProcessing"
                                    ${product.processingStrategy=='DoNotNeedProcessing'? 'checked="checked"' : ''}>
                                    <label class="col-sm-5 control-label">No</label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Status</label>
                            <div class="col-sm-8">
                                <div class="row">
                                    <input disabled type="radio" id="availableStatus" name="status" class="col-sm-1"
                                           value="Available" ${product.status=='Available'? 'checked="checked"' : ''}>
                                    <label class="col-sm-5 control-label">Available</label>
                                    <input disabled type="radio" id="notAvailableStatus" name="status" class="col-sm-1"
                                           value="NotAvailable" ${product.status=='NotAvailable'? 'checked="checked"' : ''}>
                                    <label class="col-sm-5 control-label">Not Available</label>
                                </div>
                            </div>
                        </div>
                        <div class="row hide" id="save-product">
                            <button type="button" class="btn btn-danger col-sm-5 col-xs-5" id="btn-cancel-product">
                                Cancel
                            </button>
                            <div class="col-sm-2 col-xs-2"></div>
                            <button type="submit" class="btn btn-success col-sm-5 col-xs-5" id="btn-save-product">Save
                            </button>
                        </div>
                    </div>
                </form>
                <div class="row" id="edit-and-changes">
                    <div class="col-sm-4 col-xs-0"></div>
                    <button class="btn btn-primary col-sm-5 col-xs-5" id="btn-edit-product-info">Edit product info
                    </button>
                    <div class="col-sm-4 col-xs-0"></div>
                </div>
                <h2 style="text-align: center" id="errorMessage" hidden disabled="true">${msg}</h2>
            </div>
        </main>
    </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
<script>
    function handleChange(input) {
        if (input.value < 0) input.value = 0;
        if (input.value > 365) input.value = 365;
    }
</script>
<script>
    function handlePrice(input) {
        if (input.value < 0) input.value = 0;
        if (input.value > 999) input.value = 999;
    }
</script>
<script type="text/javascript" src="<c:url value="/resources/js/product.js"/>"></script>
</body>
</html>