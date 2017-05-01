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

<form method="POST" name="Form" action="<%=request.getContextPath()%>/admin/updateTariff">
    <div class="login-form">
        <%--<h1>SELECT TARIFF</h1>--%>
        <%--<div class="form-group ">--%>
        <%--<select name="id" class="form-control" id="id">--%>
        <%--<c:forEach var="tariff" items="${allTariffs}">--%>
        <%--<option value="${tariff.id}">${tariff.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
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

        <c:if test="${not empty error}">
            <span style="float:right ; color: #10CE88;">${error}</span>
        </c:if>
        <fieldset>

            <select name="allServices" id="select-from" multiple>
                <c:forEach var="tariff" items="${servicesNotInTariff}">
                    <option value="${tariff.id}">${tariff.name}</option>
                </c:forEach>
            </select>

            <a href="JavaScript:void(0);" id="btn-add">Add &raquo;</a>
            <a href="JavaScript:void(0);" id="btn-remove">&laquo; Remove</a>

            <select name="selectto" id="select-to" multiple="multiple">
                <c:forEach var="tariff" items="${servicesByTariff}">
                    <option value="${tariff.id}">${tariff.name}</option>
                </c:forEach>
            </select>

        </fieldset>

        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</body>
</html>
