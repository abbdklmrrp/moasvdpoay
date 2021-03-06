<%--
  Created by Anna Rysakova
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="title" value="New Tariff"/>
    </jsp:include>
    <link href="<c:url value="/resources/css/price.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="AddTariff"/>
</jsp:include>
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <form method="POST" modelAttribute="product" action="${pageContext.request.contextPath}/admin/addTariff">
            <div class="login-form">
                <h1 style="text-align: center;">Create new tariff</h1>

                <c:if test="${not empty error}">
                    <h2 style="text-align: center">${error}</h2>
                </c:if>

                <div class="form-group row">
                    <label class="col-sm-4 control-label">Name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control"
                               placeholder="Name " id="name" name="name"
                               title="-   Must be only characters A-z 0-9" maxlength="25" required>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">Description</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" placeholder="Description "
                               id="Description" name="description"
                               title="-   Must be only characters A-z 0-9" maxlength="25" required>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group row" id="price">
                    <label class="col-sm-4 control-label">Base price</label>
                    <div class="col-sm-8">
                        <input type="number" class="currency" value="100.00"
                               pattern="[0-9]+([,\.][0-9]+)?" step="0.01"
                               name="basePrice" id="basePrice" required>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">Customer type</label>
                    <div class="col-sm-8">
                        <select name="customerType" class="form-control" id="customerTypeId"
                                onclick="selectedCustomer(this)">
                            <option value="Residential" selected>Residential</option>
                            <option value="Business">Business</option>
                        </select></div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">Duration in days</label>
                    <div class="col-sm-8">
                        <input type="number" name="durationInDays" class="form-control" value="365"
                               onchange="handleChange(this)" required>
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">Need processing</label>
                    <div class="col-sm-8">
                        <div class="row">
                            <input type="radio" name="processingStrategy" class="col-sm-1" value="NeedProcessing">
                            <label class="col-sm-5 control-label">Yes</label>
                            <input type="radio" name="processingStrategy" class="col-sm-1" value="DoNotNeedProcessing"
                                   checked>
                            <label class="col-sm-5 control-label">No</label>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">Status</label>
                    <div class="col-sm-8">
                        <div class="row">
                            <input type="radio" name="status" class="col-sm-1" value="Available" checked>
                            <label class="col-sm-5 control-label">Available</label>
                            <input type="radio" name="status" class="col-sm-1" value="Disable">
                            <label class="col-sm-5 control-label">Disable</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-xs-0"></div>
                    <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                    <div class="col-sm-4 col-xs-0"></div>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/price.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/selectedCustomer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/patternInputText.js"/>"></script>
<script src="${pageContext.request.contextPath}/resources/js/alertAfterUpdateProduct.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/validateDuration.js"/>"></script>
</html>