<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 23.04.2017
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW PRODUCT</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <script src="<c:url value="/resources/js/newCategoryService.js"/>"></script>
    <%--<script src="<c:url value="/resources/js/serviceTariff.js"/>"></script>--%>
</head>
<body>
<form method="POST" modelAttribute="product" action="<%=request.getContextPath()%>/admin/updateService">
    <div class="login-form">
        <%--<h1>SELECT PRODUCT</h1><br>--%>

        <%--<h6>Select product type</h6>--%>
        <%--<div class="form-group ">--%>
        <%--<select id="productType" name="productType" class="form-control" aria-required="true" onChange="Selected(this)">--%>
        <%--<c:forEach var="productType" items="${productTypes}">--%>
        <%--<option value="${productType}">${productType}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>


        <%--<div id="serviceId">--%>
        <%--<div class="form-group ">--%>
        <%--<h6>Select product</h6>--%>
        <%--<select name="id" class="form-control" >--%>
        <%--<c:forEach var="service" items="${allServices}">--%>
        <%--<option value="${service.id}">${service.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--</div>--%>

        <h1>Fill or leave empty</h1>
        <div class="form-group ">
            <h6>Enter new name</h6>
            <input type="text" class="form-control" placeholder="New name " id="Name" name="name">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Enter description</h6>
            <input type="text" class="form-control" placeholder="New description " id="Description" name="description">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Select duration in days</h6>
            <select name="durationInDays" class="form-control" id="durationInDays">
                <option value="365">365</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Need processing by admin</h6>
            <select name="needProcessing" class="form-control" id="needProcessing">
                <option value="1">Need processing</option>
                <option value="0">Do not need processing</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Select status service</h6>
            <select name="status" class="form-control" id="Status">
                <option value="1">Activate</option>
                <option value="0">Not active</option>
            </select>
        </div>
        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</body>
</html>