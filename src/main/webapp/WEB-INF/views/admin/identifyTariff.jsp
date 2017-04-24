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
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
</head>
<body>

<form method="POST" action="<%=request.getContextPath()%>/admin/identifyTariff">
    <div class="login-form">
        <h1>SELECT SERVICES</h1>
        <div class="form-group ">
            <select name="tariffs" class="form-control" id="tariffs">
                <c:forEach var="tariff" items="${tariffs}">
                    <option value="${tariff.id}">${tariff.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group ">
            <select name="servicesWI" class="form-control" id="servicesWI">
                <c:forEach var="service" items="${servicesWI}">
                    <option value="${service.id}">${service.name}</option>
                </c:forEach>
            </select>
        </div>
        <%--<div class="form-group ">--%>
        <%--<select name="services2G" class="form-control" id="services2G">--%>
        <%--<c:forEach var="service" items="${services2G}">--%>
        <%--<option value="${service.id}">${service.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="form-group ">--%>
        <%--<select name="services3G" class="form-control" id="services3G">--%>
        <%--<c:forEach var="service" items="${services3G}">--%>
        <%--<option value="${service.id}">${service.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="form-group ">--%>
        <%--<select name="services4G" class="form-control" id="services4G">--%>
        <%--<c:forEach var="service" items="${services4G}">--%>
        <%--<option value="${service.id}">${service.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--<div class="form-group ">--%>
        <%--<select name="servicesMC" class="form-control" id="servicesMC">--%>
        <%--<c:forEach var="service" items="${servicesMC}">--%>
        <%--<option value="${service.id}">${service.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</body>
</html>
