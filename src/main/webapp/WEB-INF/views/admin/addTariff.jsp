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

</head>
<body>
<form method="POST" modelAttribute="product" action="<%=request.getContextPath()%>/admin/addTariff">
    <div class="login-form">
        <h1>ENTER NEW TARIFF</h1>

        <div class="form-group ">
            <h6>Enter name</h6>
            <input type="text" class="form-control" placeholder="Name " id="Name" name="name">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Enter description</h6>
            <input type="text" class="form-control" placeholder="Description " id="Description" name="description">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Select duration</h6>
            <select name="duration" class="form-control" id="duration">
                <option value="365">365 days</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Need processing by admin</h6>
            <select name="needProcessing" class="form-control" id="needProcessing">
                <option value="1">Yes</option>
                <option value="0">No</option>
            </select>
        </div>

        <div class="form-group ">
            <h6>Select tariff</h6>
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