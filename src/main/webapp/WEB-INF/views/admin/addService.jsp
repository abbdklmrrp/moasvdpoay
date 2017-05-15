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
    <title>ADD NEW SERVICE</title>
    <link href="<c:url value="/resources/css/basic.css"/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("jquery", "1.4.4");
    </script>
    <script src="<c:url value="/resources/js/newCategoryService.js"/>"></script>
</head>
<body>
<form method="POST" modelAttribute="newProductCategories" modelAttribute="product"
      action="<%=request.getContextPath()%>/admin/addService">
    <div class="login-form">
        <h1>ENTER NEW SERVICE</h1><br>

        <c:if test="${not empty errorService}">
            <span style="float:right ; color: #10CE88;">${errorService}</span>
        </c:if>

        <div id='categoryId'>
            <div class="form-group ">
                <h6>Select category</h6>
                <select name="categoryId" class="form-control" onclick="SelectedCategory(this)">
                    <c:forEach var="category" items="${productCategories}">
                        <option value="${category.id}">${category.categoryName}</option>
                    </c:forEach>
                    <option value="">New category</option>
                </select>
            </div>
        </div>

        <div id="newCategory" style="display:none">
            <div class="form-group ">
                <h6>Enter new category</h6>
                <input type="text" class="form-control" placeholder="New category " name="categoryName" id="newCategoryID">
                <i class="fa fa-user"></i>
            </div>
        </div>

        <div id="newCategoryDesc" style="display: none">
            <div class="form-group ">
                <h6>Enter new category description</h6>
                <input type="text" class="form-control" placeholder="New category description "
                       name="categoryDescription" id="newCategoryDescID">
                <i class="fa fa-user"></i>
            </div>
        </div>

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
            <h6>Enter base price</h6>
            <input type="text" class="form-control" placeholder="0" id="basePrice" name="basePrice" value="0">
            <i class="fa fa-user"></i>
        </div>

        <div class="form-group ">
            <h6>Customer type</h6>
            <select name="customerType" class="form-control" id="customerType">
                <option value="Business">Business</option>
                <option value="Residential">Residential</option>
            </select>
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