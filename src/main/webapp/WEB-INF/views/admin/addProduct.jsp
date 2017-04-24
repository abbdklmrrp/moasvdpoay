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
    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

</head>
<body>
<div id="bg"></div>

<form method="POST" action="<%=request.getContextPath()%>/admin/addProduct">

    <div class="login-form">
        <div class="form-group ">
            <select name="productTypes" class="select" id="productTypes">
                <c:forEach var="types" items="${productTypes}">
                    <option value="${types.id}">${types.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group ">
            <select name="productCategories" class="select" id="select">
                <c:forEach var="category" items="${productCategories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group ">
            <select name="duration" class="select" id="duration">
                <option value="12">12 month</option>
            </select>
        </div>

        <div class="form-group ">
            <select name="needProcessing" class="select" id="needProcessing">
                <option value="1">need processing</option>
                <option value="0">don't need processing</option>
            </select>
        </div>

        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Name " id="Name" name="name">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <input type="text" class="form-control" placeholder="Description " id="Description" name="description">
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group ">

            <select name="status" class="select" id="Status">
                <option value="1">status activated</option>
                <option value="0">status suspended</option>
            </select>
        </div>
        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</div>
</body>
</html>
