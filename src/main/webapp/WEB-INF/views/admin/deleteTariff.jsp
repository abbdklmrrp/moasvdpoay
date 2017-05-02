<%--
  Created by IntelliJ IDEA.
  User: Nikita
  Date: 02.05.2017
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>DELETE TARIFF</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <script>

    </script>
</head>
<body>
<div class="login-form">
    <form:form method="post" action="deleteTariff">
        <div class="form-group ">
            <h1>DELETE TARIFF</h1><br>
            <select name="tariff" class="form-control" id="tariff">
                <c:forEach items="${productList}" var="obj">
                    <option value="${obj.id}">${obj.name}</option>
                </c:forEach>
            </select>
        </div>
        <br>
        <button type="submit" class="log-btn">Delete</button>
    </form:form>
</div>
</body>
</html>
