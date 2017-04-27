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
    <script src="<c:url value="/resources/js/serviceTariff.min.js"/>"></script>

</head>
<body>
<form method="POST" action="<%=request.getContextPath()%>/admin/addProduct">
    <div class="login-form">
        <h1>ENTER NEW PRODUCT</h1>
        <div class="form-group ">
            <select id="productTypes" name="productTypes" class="form-control" aria-required="true"
                    onChange="Selected(this)">
                <c:forEach var="types" items="${productTypes}">
                    <option value="${types.id}">${types.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group ">
            <div id='Block1' style='display: none;'>
                <select name="productCategories" class="form-control" id="productCategories">
                    <option value="" selected="selected">-</option>
                    <c:forEach var="category" items="${productCategories}">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group ">
            <select name="duration" class="form-control" id="duration">
                <option value="12">12 month</option>
            </select>
        </div>

        <div class="form-group ">
            <select name="needProcessing" class="form-control" id="needProcessing">
                <option value="1">Need processing</option>
                <option value="0">Do not need processing</option>
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
            <select name="status" class="form-control" id="Status">
                <option value="1">Status activated</option>
                <option value="0">Status suspended</option>
            </select>
        </div>
        <button type="submit" class="log-btn">Save</button>
    </div>
</form>
</div>
</body>
</html>