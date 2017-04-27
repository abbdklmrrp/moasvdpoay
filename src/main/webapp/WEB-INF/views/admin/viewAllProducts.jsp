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
</head>
<body>

<div class="login-form">
    <div class="form-group ">
        <select name="allTariffs" class="form-control" id="tariffs">
            <c:forEach var="tariff" items="${allTariffs}">
            </c:forEach>
        </select>
    </div>
    <div class="form-group ">
        <select name="allServices" class="form-control" id="servicesWI">
            <c:forEach var="service" items="${allServices}">
            </c:forEach>
        </select>
    </div>

    <button type="submit" class="log-btn">Save</button>
</div>
</body>
</html>
