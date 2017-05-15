<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 23.04.2017
  Time: 23:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW TARIFF</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
</head>
<body>
<form method="POST" modelAttribute="product" action="${pageContext.request.contextPath}/admin/addTariff">
    <div class="login-form">
        <h1>ENTER NEW TARIFF</h1><br>

        <c:if test="${not empty errorEmptyFilling}">
            <span style="float:right ; color: #10CE88;">${errorEmptyFilling}</span>
        </c:if>

        <div class="form-group ">
            <h6>Enter name</h6>
            <input type="text" class="form-control" placeholder="Name " id="Name" name="name" value="" required>
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Enter description</h6>
            <input type="text" class="form-control" placeholder="Description " id="Description" name="description"
                   value="" required>
            <i class="fa fa-user"></i>
        </div>


        <div class="form-group row">
            <label class="col-sm-4 control-label">Enter base price</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" placeholder="0 "
                       id="basePrice" name="basePrice" required>
                <i class="fa fa-user"></i>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-4 control-label">Customer type</label>
            <div class="col-sm-8">
                <select name="customerType" class="form-control" id="customerTypeId" required>
                    <option value="Business">Business</option>
                    <option value="Residential">Residential</option>
                </select></div>
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
        <button type="submit" class="log-btn">Save</button>
        <br>
        <h2><a href="${pageContext.request.contextPath}/doLogout">Logout</a></h2>

    </div>
</form>
</body>
</html>