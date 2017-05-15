<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 24.04.2017
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IDENTIFY TARIFF</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <script src="<c:url value="/resources/js/categoryService.js"/>"></script>
</head>
<body>

<form method="POST" name="Form" action="${pageContext.request.contextPath}/admin/updateTariff">
    <div class="login-form">

        <h1>Fill or leave empty</h1>
        <div class="form-group ">
            <h6>Enter name</h6>
            <input type="text" class="form-control" placeholder="Name " id="Name" name="name" value="${product.name}">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Enter description</h6>
            <input type="text" class="form-control" placeholder="Description " id="Description" name="description"
                   value="${product.description}">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Enter base price</h6>
            <input type="text" class="form-control" placeholder="0" id="basePrice" name="basePrice"
                   value="${product.basePrice}">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Customer type</h6>
            <select name="customerType" class="form-control" id="customerType">
                <option value="Business">Business</option>
                <option value="Residential">Residential</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Select duration in days</h6>
            <select name="durationInDays" class="form-control" id="durationInDays">
                <option value="365">365</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Need processing by admin</h6>
            <select name="processingStrategy" class="form-control" id="processingStrategy">
                <option value="NeedProcessing">Need processing</option>
                <option value="DoNotNeedProcessing">Do not need processing</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Select status service</h6>
            <select name="status" class="form-control" id="Status">
                <option value="Available">Activate</option>
                <option value="NotAvailable">Not active</option>
            </select>
        </div>

        <c:if test="${not empty errors}">
            <span style="float:right ; color: #10CE88;">${errors}</span>
        </c:if>
        <fieldset>

            <h6>All categories</h6>
            <select name="categoriesID" id="categoriesID" multiple>
                <c:forEach var="category" items="${allServices}">
                    <option value="${category.categoryName}">${category.categoryName}</option>
                </c:forEach>
            </select>

            <h6>Selected services</h6>
            <select name="selectto" id="selectto" multiple="multiple">
            </select>

            <a href="JavaScript:void(0);" id="btn-add">Add&raquo;</a>
            <h6>Selected services</h6>
            <select name="selectedService" id="selectedService" multiple="multiple">
                <c:forEach var="tariff" items="${servicesByTariff}">
                    <option value="${tariff.id}" selected>${tariff.name}</option>
                </c:forEach>
            </select>
            <a href="JavaScript:void(0);" id="btn-remove">&laquo; Remove</a>

        </fieldset>

        <button type="submit" class="log-btn">Save</button>
        <br>
        <h2><a href="${pageContext.request.contextPath}/doLogout">Logout</a></h2>
    </div>
</form>
</body>
</html>
