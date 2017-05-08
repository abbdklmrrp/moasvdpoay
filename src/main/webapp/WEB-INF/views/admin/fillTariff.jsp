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
<form action="/admin/fillTariff" method="post">
    <div class="login-form">
        <h1>SELECT SERVICES</h1>
        <div class="form-group ">
            <select name="tariffId" class="form-control" id="tariffs">
                <c:forEach var="tariff" items="${tariffs}">
                    <option value="${tariff.id}">${tariff.name}</option>
                </c:forEach>
            </select>
        </div>

        <c:if test="${not empty errors}">
            <span style="float:right ; color: #10CE88;">${errors}</span>
        </c:if>
        <c:if test="${not empty errorUniqueCategory}">
            <span style="float:right ; color: #10CE88;">${errorUniqueCategory}</span>
        </c:if>

        <fieldset>
            <h6>All services</h6>
            <select name="categoriesID" id="categoriesID" multiple>
                <c:forEach var="category" items="${allServices}">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </select>

            <h6>Selected services</h6>
            <select name="selectto" id="selectto" multiple="multiple">
            </select>

            <a href="JavaScript:void(0);" id="btn-add">Add&raquo;</a>
            <h6>Selected services</h6>
            <select name="selectedService" id="selectedService" multiple="multiple">
            </select>
            <a href="JavaScript:void(0);" id="btn-remove">&laquo; Remove</a>

            <button type="submit" class="log-btn" id="submit">Save</button>

        </fieldset>

    </div>
</form>
</body>
</html>